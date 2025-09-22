package gsm.gsmjava.domain.posting.service;

import gsm.gsmjava.domain.application.repository.ApplicationRepository;
import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.posting.repository.PostingRepository;
import gsm.gsmjava.domain.posting.service.dto.req.CreatePostingReqDto;
import gsm.gsmjava.domain.posting.service.dto.res.QueryPostingDetailResDto;
import gsm.gsmjava.domain.posting.service.dto.res.QueryPostingResDto.PositionDto;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.global.error.ExpectedException;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryPostingDetailService {

    private final PostingRepository postingRepository;
    private final ApplicationRepository applicationRepository;
    private final UserUtil userUtil;

    @Transactional(readOnly = true)
    public QueryPostingDetailResDto queryOne(Long postingId) {
        Posting posting = postingRepository.findById(postingId).orElseThrow(
                () -> new ExpectedException("해당 공고가 존재하지 않습니다. id = " + postingId, HttpStatus.NOT_FOUND)
        );

        User currentUser = userUtil.getCurrentUser();
        boolean applied = applicationRepository.isApplied(posting.getId(), currentUser.getId());

        return QueryPostingDetailResDto.builder()
                .id(posting.getId())
                .companyName(posting.getCompanyName())
                .companyDescription(posting.getCompanyDescription())
                .companyLocation(posting.getCompanyLocation())
                .employmentType(posting.getEmploymentType())
                .positions(posting.getPostingPositions().stream()
                    .map(position -> PositionDto.builder()
                            .id(position.getPosition().getId())
                            .name(position.getPosition().getName())
                            .build())
                    .toList())
                .applicationCount(posting.getApplicationCount())
                .createdAt(posting.getCreatedAt())
                .startAt(posting.getPostingStartAt())
                .endAt(posting.getPostingEndAt())
                .applied(applied)
                .build();
    }

}
