package gsm.gsmjava.domain.application.controller;

import gsm.gsmjava.domain.application.service.ApplyService;
import gsm.gsmjava.domain.application.service.QueryApplicationService;
import gsm.gsmjava.domain.application.service.QueryMyApplicationService;
import gsm.gsmjava.domain.application.service.dto.req.ApplyReqDto;
import gsm.gsmjava.domain.application.service.dto.res.ApplicationResDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplyService applyService;
    private final QueryMyApplicationService queryMyApplicationService;
    private final QueryApplicationService queryApplicationService;

    @PostMapping("/apply/{posting_id}")
    public ResponseEntity<Void> apply(
            @PathVariable("posting_id") Long postingId,
            @RequestBody @Valid ApplyReqDto reqDto
        ) {
        applyService.apply(postingId, reqDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<MyApplicationResDto> queryMy() {
        MyApplicationResDto response = queryMyApplicationService.queryMy();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{posting_id}")
    public ResponseEntity<ApplicationResDto> queryByPostingId(
            @PathVariable("posting_id") Long postingId
    ) {
        ApplicationResDto response = queryApplicationService.queryByPosting(postingId);
        return ResponseEntity.ok(response);
    }

}
