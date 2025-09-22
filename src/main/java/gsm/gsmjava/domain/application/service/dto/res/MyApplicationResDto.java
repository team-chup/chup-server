package gsm.gsmjava.domain.application.service.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import gsm.gsmjava.domain.application.type.ApplicationStatus;
import gsm.gsmjava.domain.applicationresult.type.ApplicationResultStatus;
import gsm.gsmjava.domain.posting.type.CompanyLocation;
import gsm.gsmjava.domain.posting.type.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MyApplicationResDto {
    private Integer count;
    private List<MyApplicationDto> applications;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MyApplicationDto {
        private Long id;
        private Long postingId;
        private String companyName;
        private CompanyLocation companyLocation;
        private EmploymentType employmentType;
        private PositionDto position;
        private ApplicationStatus status;
        private ResumeDto resume;
        private PortfolioDto portfolio;
        private ResultDto result;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime startAt;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime endAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ResumeDto {
        private String name;
        private String url;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PortfolioDto {
        private String name;
        private String url;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PositionDto {
        private Long id;
        private String name;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ResultDto {
        private ApplicationResultStatus status;
        private String failedReason;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime announcedAt;
    }
}
