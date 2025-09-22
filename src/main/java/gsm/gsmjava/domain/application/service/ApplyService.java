package gsm.gsmjava.domain.application.service;

import gsm.gsmjava.domain.application.entity.Application;
import gsm.gsmjava.domain.application.event.ApplyEvent;
import gsm.gsmjava.domain.application.repository.ApplicationRepository;
import gsm.gsmjava.domain.application.service.dto.req.ApplyReqDto;
import gsm.gsmjava.domain.application.type.ApplicationStatus;
import gsm.gsmjava.domain.position.entity.Position;
import gsm.gsmjava.domain.position.repository.PositionRepository;
import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.posting.repository.PostingRepository;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.global.error.ExpectedException;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static gsm.gsmjava.global.cache.CacheConstant.POSTING_LIST_CACHE;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final PostingRepository postingRepository;
    private final PositionRepository positionRepository;
    private final UserUtil userUtil;
    private final ApplicationRepository applicationRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @CacheEvict(value = POSTING_LIST_CACHE, key = "'ALL'", cacheManager = "cacheManager")
    public void apply(Long postingId, ApplyReqDto reqDto) {
        LocalDateTime now = LocalDateTime.now();
        Posting posting = postingRepository.queryNotEndById(now, postingId).orElseThrow(
                () -> new ExpectedException("해당 공고가 존재하지 않거나 마감되었습니다. id = " + postingId, HttpStatus.NOT_FOUND)
        );
        Position position = positionRepository.findById(reqDto.getPositionId()).orElseThrow(
                () -> new ExpectedException("해당 포지션은 존재하지 않습니다. id = " + reqDto.getPositionId(), HttpStatus.NOT_FOUND)
        );
        User user = userUtil.getCurrentUser();

        boolean applied = applicationRepository.findForUpdateByUserAndPosting(user, posting).isPresent();
        if (applied) {
            throw new ExpectedException("이미 해당 공고에 지원하였습니다.", HttpStatus.BAD_REQUEST);
        }

        Application application = Application.builder()
                .posting(posting)
                .position(position)
                .user(user)
                .createdAt(now)
                .applicantName(user.getName())
                .applicantEmail(user.getEmail())
                .applicantPhoneNumber(user.getPhoneNumber())
                .applicantStudentNumber(user.getStudentNumber())
                .applicantResumeUrl(user.getResume().getUrl())
                .applicantResumeName(user.getResume().getName())
                .applicantPortfolioUrl(user.getPortfolio().getUrl())
                .applicantPortfolioName(user.getPortfolio().getName())
                .applicantStatus(ApplicationStatus.PENDING)
                .build();
        Application newApplication = applicationRepository.save(application);

        posting.add();
        postingRepository.save(posting);

        applicationEventPublisher.publishEvent(
                ApplyEvent.builder()
                        .applicationId(newApplication.getId())
                        .positingId(posting.getId())
                        .companyName(posting.getCompanyName())
                        .positionName(position.getName())
                        .applicantDate(application.getCreatedAt())
                        .applicantName(application.getApplicantName())
                        .applicantEmail(application.getApplicantEmail())
                        .applicantPhoneNumber(application.getApplicantPhoneNumber())
                        .build()
        );
    }


}
