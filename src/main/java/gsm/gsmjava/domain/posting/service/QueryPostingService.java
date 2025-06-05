package gsm.gsmjava.domain.posting.service;

import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.posting.repository.PostingRepository;
import gsm.gsmjava.domain.posting.service.dto.res.QueryPostingResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static gsm.gsmjava.global.cache.CacheConstant.POSTING_LIST_CACHE;

@Service
@RequiredArgsConstructor
public class QueryPostingService {

    private final PostingRepository postingRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = POSTING_LIST_CACHE, cacheManager = "cacheManager")
    public QueryPostingResDto queryAll() {
        LocalDateTime now = LocalDateTime.now();
        List<Posting> postings = postingRepository.queryNotEnd(now);

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
