package gsm.gsmjava.domain.application.controller;

import gsm.gsmjava.domain.application.service.ApplyService;
import gsm.gsmjava.domain.application.service.dto.req.ApplyReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplyService applyService;

    @PostMapping("/{positing_id}")
    public ResponseEntity<Void> apply(
            @PathVariable("positing_id") Long positionId,
            @RequestBody ApplyReqDto reqDto
        ) {
        applyService.apply(positionId, reqDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
