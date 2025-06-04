package gsm.gsmjava.domain.posting.controller;

import gsm.gsmjava.domain.posting.service.CreatePostingService;
import gsm.gsmjava.domain.posting.service.dto.req.CreatePostingReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posting")
@RequiredArgsConstructor
public class PostingController {

    private final CreatePostingService createPostingService;

    @PostMapping
    public ResponseEntity<Void> create(
        @RequestBody CreatePostingReqDto reqDto
    ) {
        createPostingService.create(reqDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
