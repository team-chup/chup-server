package gsm.gsmjava.domain.file.controller;

import gsm.gsmjava.domain.file.controller.dto.res.FileResDto;
import gsm.gsmjava.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/resume")
    public FileResDto uploadResumeFile(
            @RequestParam(value = "file") MultipartFile multipartFile
    ) {
        String url = fileService.uploadResumeFile(multipartFile);
        return new FileResDto(url);
    }

    @PostMapping("/posting")
    public FileResDto uploadPostingFile(
            @RequestParam(value = "file") MultipartFile multipartFile
    ) {
        String url = fileService.uploadPostingFile(multipartFile);
        return new FileResDto(url);
    }

}
