package gsm.gsmjava.domain.posting.controller;

import gsm.gsmjava.domain.position.service.CreatePositionService;
import gsm.gsmjava.domain.position.service.QueryPositionService;
import gsm.gsmjava.domain.position.service.dto.req.CreatePositionReqDto;
import gsm.gsmjava.domain.position.service.dto.res.QueryPositionResDto;
import gsm.gsmjava.domain.posting.service.CreatePostingService;
import gsm.gsmjava.domain.posting.service.QueryPostingDetailService;
import gsm.gsmjava.domain.posting.service.QueryPostingService;
import gsm.gsmjava.domain.posting.service.UpdatePostingService;
import gsm.gsmjava.domain.posting.service.dto.req.CreatePostingReqDto;
import gsm.gsmjava.domain.posting.service.dto.req.UpdatePostingReqDto;
import gsm.gsmjava.domain.posting.service.dto.res.QueryPostingDetailResDto;
import gsm.gsmjava.domain.posting.service.dto.res.QueryPostingResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posting")
@RequiredArgsConstructor
public class PostingController {

    private final CreatePostingService createPostingService;
    private final QueryPostingService queryPostingService;
    private final QueryPostingDetailService queryPostingDetailService;
    private final CreatePositionService createPositionService;
    private final QueryPositionService queryPositionService;
    private final UpdatePostingService updatePostingService;

    @PostMapping
    public ResponseEntity<Void> create(
        @RequestBody @Valid CreatePostingReqDto reqDto
    ) {
        createPostingService.create(reqDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<QueryPostingResDto> queryAll() {
        QueryPostingResDto response = queryPostingService.queryAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{posting_id}")
    public ResponseEntity<QueryPostingDetailResDto> queryOne(
        @PathVariable("posting_id") Long postingId
    ) {
        QueryPostingDetailResDto response = queryPostingDetailService.queryOne(postingId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{posting_id}")
    public ResponseEntity<Void> update(
            @PathVariable("posting_id") Long postingId,
            @RequestBody @Valid UpdatePostingReqDto reqDto
    ) {
        updatePostingService.update(postingId, reqDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/position")
    public ResponseEntity<Void> createPosition(
            @RequestBody @Valid CreatePositionReqDto reqDto
    ) {
        createPositionService.create(reqDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/position")
    public ResponseEntity<QueryPositionResDto> queryPosition() {
        QueryPositionResDto response = queryPositionService.queryAll();
        return ResponseEntity.ok(response);
    }

}
