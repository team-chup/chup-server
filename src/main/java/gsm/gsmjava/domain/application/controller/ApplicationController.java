package gsm.gsmjava.domain.application.controller;

import gsm.gsmjava.domain.application.service.*;
import gsm.gsmjava.domain.application.service.dto.req.AnnounceReqDto;
import gsm.gsmjava.domain.application.service.dto.req.ApplyReqDto;
import gsm.gsmjava.domain.application.service.dto.res.ApplicationResDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplyService applyService;
    private final QueryMyApplicationService queryMyApplicationService;
    private final QueryApplicationService queryApplicationService;
    private final AnnounceResultService announceResultService;
    private final DownloadService downloadService;

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

    @PostMapping("/announce/{application_id}")
    public ResponseEntity<Void> announce(
            @PathVariable("application_id") Long applicationId,
            @RequestBody @Valid AnnounceReqDto reqDto
    ) {
        announceResultService.announce(applicationId, reqDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/download/{posting_id}")
    public ResponseEntity<byte[]> download(
        @PathVariable("posting_id") Long postingId,
        @RequestParam("ids") List<Long> ids
    ) {
        byte[] zipData = downloadService.generateResumeZip(postingId, ids);

        String fileName = URLEncoder.encode("이력서_모음.zip", StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(zipData.length);
        headers.setContentDispositionFormData("attachment", fileName);

        return ResponseEntity.ok().headers(headers).body(zipData);
    }

}
