package gsm.gsmjava.domain.posting.service;

import gsm.gsmjava.domain.position.entity.Position;
import gsm.gsmjava.domain.position.repository.PositionRepository;
import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.posting.event.CreatePostingEvent;
import gsm.gsmjava.domain.posting.repository.PostingRepository;
import gsm.gsmjava.domain.posting.service.dto.req.CreatePostingReqDto;
import gsm.gsmjava.domain.postingfile.entity.PostingFile;
import gsm.gsmjava.domain.postingfile.repository.PostingFileRepository;
import gsm.gsmjava.domain.postingposition.entity.PostingPosition;
import gsm.gsmjava.domain.postingposition.repository.PostingPositionRepository;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.global.error.ExpectedException;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePostingService {

    private final UserUtil userUtil;
    private final PositionRepository positionRepository;
    private final PostingPositionRepository postingPositionRepository;
    private final PostingFileRepository postingFileRepository;
    private final PostingRepository postingRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void create(CreatePostingReqDto reqDto) {
        if (reqDto.getFiles().size() > 5) {
            throw new ExpectedException("최대 5개의 파일만 업로드할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }

        User currentUser = userUtil.getCurrentUser();

        Posting posting = Posting.builder()
                .user(currentUser)
                .companyName(reqDto.getCompanyName())
                .companyDescription(reqDto.getCompanyDescription())
                .companyLocation(reqDto.getCompanyLocation())
                .employmentType(reqDto.getEmploymentType())
                .postingStartAt(reqDto.getStartAt())
                .postingEndAt(reqDto.getEndAt())
                .build();
        Posting newPosting = postingRepository.save(posting);

        List<Position> positions = positionRepository.queryIds(reqDto.getPositions());
        List<PostingPosition> postingPositions = positions.stream()
            .map(position ->
                PostingPosition.builder()
                        .posting(newPosting)
                        .position(position)
                        .build()
            ).toList();
        postingPositionRepository.saveAll(postingPositions);

        List<PostingFile> postingFiles = reqDto.getFiles().stream()
            .map(file ->
                    PostingFile.builder()
                            .posting(newPosting)
                            .name(file.getName())
                            .url(file.getUrl())
                            .build()
            ).toList();
        postingFileRepository.saveAll(postingFiles);

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
