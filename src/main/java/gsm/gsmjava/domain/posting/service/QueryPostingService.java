package gsm.gsmjava.domain.posting.service;

import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.posting.repository.PostingRepository;
import gsm.gsmjava.domain.posting.service.dto.res.QueryPostingResDto;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.domain.user.type.Authority;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryPostingService {

    private final PostingRepository postingRepository;
    private final UserUtil userUtil;

    @Transactional(readOnly = true)
    public QueryPostingResDto queryAll() {
        User currentUser = userUtil.getCurrentUser();
        Authority authority = currentUser.getAuthority();

        LocalDateTime now = LocalDateTime.now();
        List<Posting> postings =
                authority == Authority.USER
                        ? postingRepository.queryNotEndFetchJoin(now)
                        : postingRepository.queryAllFetchJoin();

        return QueryPostingResDto.builder()
                .count(postings.size())
                .postings(postings.stream()
                    .map(posting -> QueryPostingResDto.PostingDto.builder()
                        .id(posting.getId())
                        .companyName(posting.getCompanyName())
                        .companyLocation(posting.getCompanyLocation())
                        .employmentType(posting.getEmploymentType())
                        .positions(posting.getPostingPositions().stream()
                            .map(position -> QueryPostingResDto.PositionDto.builder()
                                .id(position.getPosition().getId())
                                .name(position.getPosition().getName())
                                .build())
                            .toList())
                        .applicationCount(posting.getApplicationCount())
                        .createdAt(posting.getCreatedAt())
                        .startAt(posting.getPostingStartAt())
                        .endAt(posting.getPostingEndAt())
                        .build())
                    .toList())
                .build();
    }

}
