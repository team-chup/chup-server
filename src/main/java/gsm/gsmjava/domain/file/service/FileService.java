package gsm.gsmjava.domain.file.service;

import gsm.gsmjava.global.error.ExpectedException;
import gsm.gsmjava.infra.s3.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final UploadFileService uploadFileService;

    public String uploadResumeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ExpectedException("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        String originalFileName = file.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);

        validateResumeFileContentType(fileExtension);
        return uploadFileService.execute(file, fileExtension, "resume");
    }

    public String uploadPostingFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ExpectedException("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        String originalFileName = file.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);

        validatePostingFileContentType(fileExtension);
        return uploadFileService.execute(file, fileExtension, "posting");
    }

    private void validateResumeFileContentType(String fileExtension) {
        List<String> allowExtension = List.of("pdf");
        if (!allowExtension.contains(fileExtension.toLowerCase())) {
            throw new ExpectedException("지원하지 않는 파일 확장자 입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validatePostingFileContentType(String fileExtension) {
        List<String> allowExtension = List.of("pdf", "jpg", "jpeg", "png", "xls", "xlsx", "xlsm", "hwp", "hwpx", "hwt", "ppt", "pptx", "zip");
        if (!allowExtension.contains(fileExtension.toLowerCase())) {
            throw new ExpectedException("지원하지 않는 파일 확장자 입니다.", HttpStatus.BAD_REQUEST);
        }
    }

}
