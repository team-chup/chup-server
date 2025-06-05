package gsm.gsmjava.domain.posting.controller;

import gsm.gsmjava.domain.posting.service.CreatePostingService;
import gsm.gsmjava.domain.posting.service.QueryPostingService;
import gsm.gsmjava.domain.posting.service.dto.req.CreatePostingReqDto;
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

}
