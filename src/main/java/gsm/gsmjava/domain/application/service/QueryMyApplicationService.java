package gsm.gsmjava.domain.application.service;

import gsm.gsmjava.domain.application.entity.Application;
import gsm.gsmjava.domain.application.repository.ApplicationRepository;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.*;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryMyApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserUtil userUtil;

    @Transactional(readOnly = true)
    public MyApplicationResDto queryMy() {
        User currentUser = userUtil.getCurrentUser();
        List<Application> applications = applicationRepository.findByUserFetchJoin(currentUser);

        List<MyApplicationDto> applicationDtoes = applications.stream().map(application -> MyApplicationDto.builder()
                .id(application.getId())
                .postingId(application.getPosting().getId())
                .companyName(application.getPosting().getCompanyName())
                .companyLocation(application.getPosting().getCompanyLocation())
                .employmentType(application.getPosting().getEmploymentType())
                .resume(ResumeDto.builder()
                        .name(application.getApplicantResumeName())
                        .url(application.getApplicantResumeUrl())
                        .build())
                .portfolio(PortfolioDto.builder()
                        .name(application.getApplicantPortfolioName())
                        .url(application.getApplicantPortfolioUrl())
                        .build())
                .position(PositionDto.builder()
                        .id(application.getPosition().getId())
                        .name(application.getPosition().getName())
                        .build())
                .status(application.getApplicantStatus())
                .result(application.getApplicationResult() == null ? null : ResultDto.builder()
                        .status(application.getApplicationResult().getApplicationResultStatus())
                        .failedReason(application.getApplicationResult().getApplicationFailedReason())
                        .announcedAt(application.getApplicationResult().getCreatedAt())
                        .build())
                .startAt(application.getPosting().getPostingStartAt())
                .endAt(application.getPosting().getPostingEndAt())
                .createdAt(application.getCreatedAt())
                .build()
        ).toList();

        return new MyApplicationResDto(applications.size(), applicationDtoes);
    }

}
