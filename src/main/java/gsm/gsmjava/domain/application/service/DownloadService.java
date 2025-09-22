package gsm.gsmjava.domain.application.service;

import gsm.gsmjava.domain.application.entity.Application;
import gsm.gsmjava.domain.application.repository.ApplicationRepository;
import gsm.gsmjava.global.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class DownloadService {

    private final ApplicationRepository applicationRepository;

    @Transactional(readOnly = true)
    public byte[] generateResumeZip(Long postingId, List<Long> applicationIds) {
        List<Application> applications = applicationRepository.findByPostingIdAndIds(postingId, applicationIds);
        if (applications.isEmpty()) {
            throw new ExpectedException("지원자 데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        try {
            ByteArrayOutputStream zipOutStream = new ByteArrayOutputStream();
            try (ZipOutputStream zip = new ZipOutputStream(zipOutStream)) {
                for (Application app : applications) {
                    String name = app.getUser().getName();
                    String studentNumber = app.getUser().getStudentNumber();
                    String position = app.getPosition().getName();
                    String company = app.getPosting().getCompanyName();
                    String extension = "txt";
                    String resumeName = sanitize(name) + "_" + sanitize(studentNumber) + "_" + sanitize(position) + "_이력서." + extension;

                    byte[] resumeBytes = createTxtWithLink(
                            app.getApplicantResumeName(), app.getApplicantResumeUrl(),
                            app.getApplicantPortfolioName(), app.getApplicantPortfolioUrl()
                    );

                    String pathInZip = sanitize(company) + "_이력서_모음/" + resumeName;
                    zip.putNextEntry(new ZipEntry(pathInZip));
                    zip.write(resumeBytes);
                    zip.closeEntry();
                }
            }

            return zipOutStream.toByteArray();
        } catch (IOException e) {
            throw new ExpectedException("다운로드 중 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] createTxtWithLink(String resumeName, String resumeUrl, String portfolioName, String portfolioUrl) {
        String content = resumeName != null ? resumeName + ": " + resumeUrl : "";
        content += portfolioName != null ? portfolioName + ": " + portfolioUrl : "";
        return content.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    private String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9가-힣._-]", "_");
    }
}
