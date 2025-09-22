package gsm.gsmjava.domain.posting.service;

import gsm.gsmjava.domain.position.entity.Position;
import gsm.gsmjava.domain.position.repository.PositionRepository;
import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.posting.event.CreatePostingEvent;
import gsm.gsmjava.domain.posting.repository.PostingRepository;
import gsm.gsmjava.domain.posting.service.dto.req.CreatePostingReqDto;
import gsm.gsmjava.domain.postingposition.entity.PostingPosition;
import gsm.gsmjava.domain.postingposition.repository.PostingPositionRepository;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static gsm.gsmjava.global.cache.CacheConstant.POSTING_LIST_CACHE;

@Service
@RequiredArgsConstructor
public class CreatePostingService {

    private final UserUtil userUtil;
    private final PositionRepository positionRepository;
    private final PostingPositionRepository postingPositionRepository;
    private final PostingRepository postingRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @CacheEvict(value = POSTING_LIST_CACHE, key = "'ALL'", cacheManager = "cacheManager")
    public void create(CreatePostingReqDto reqDto) {
        User currentUser = userUtil.getCurrentUser();

        Posting posting = Posting.builder()
                .user(currentUser)
                .companyName(reqDto.getCompanyName())
                .companyDescription(reqDto.getCompanyDescription())
                .companyLocation(reqDto.getCompanyLocation())
                .employmentType(reqDto.getEmploymentType())
                .postingStartAt(reqDto.getStartAt())
                .postingEndAt(reqDto.getEndAt())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .applicationCount(0)
                .build();
        Posting newPosting = postingRepository.save(posting);

        List<Position> positions = positionRepository.queryIds(reqDto.getPositions().stream().distinct().toList());
        List<PostingPosition> postingPositions = positions.stream()
            .map(position ->
                PostingPosition.builder()
                        .posting(newPosting)
                        .position(position)
                        .build()
            ).toList();
        postingPositionRepository.saveAll(postingPositions);

        applicationEventPublisher.publishEvent(
            CreatePostingEvent.builder()
                .postingId(newPosting.getId())
                .companyName(reqDto.getCompanyName())
                .companyDescription(
                    reqDto.getCompanyDescription().substring(
                        0, Math.min(100, reqDto.getCompanyDescription().length())
                    )
                )
                .companyLocation(reqDto.getCompanyLocation())
                .employmentType(reqDto.getEmploymentType())
                .positions(positions.stream().map(Position::getName).toList())
                .startAt(reqDto.getStartAt())
                .endAt(reqDto.getEndAt())
                .build()
        );
    }

}
