package gsm.gsmjava.domain.application.service.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.PortfolioDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.PositionDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.ResultDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.ResumeDto;
import gsm.gsmjava.domain.application.type.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ApplicationResDto {
    private Integer count;
    private List<ApplicationDto> applications;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ApplicantDto {
        private String name;
        private String email;
        private String phoneNumber;
        private String studentNumber;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ApplicationDto {
        private Long id;
        private ApplicantDto applicant;
        private PositionDto position;
        private ApplicationStatus status;
        private ResumeDto resume;
        private PortfolioDto portfolio;
        private ResultDto result;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;
    }
}
