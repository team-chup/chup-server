package gsm.gsmjava.domain.application.controller;

import gsm.gsmjava.domain.application.service.ApplyService;
import gsm.gsmjava.domain.application.service.dto.req.ApplyReqDto;
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

    @PostMapping("/apply/{posting_id}")
    public ResponseEntity<Void> apply(
            @PathVariable("posting_id") Long postingId,
            @RequestBody @Valid ApplyReqDto reqDto
        ) {
        applyService.apply(postingId, reqDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
