package gsm.gsmjava.domain.posting.service.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import gsm.gsmjava.domain.posting.type.CompanyLocation;
import gsm.gsmjava.domain.posting.type.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryPostingResDto {
    private Integer count;
    private List<PostingDto> postings;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostingDto {
        private Long id;
        private String companyName;
        private CompanyLocation companyLocation;
        private EmploymentType employmentType;
        private List<PositionDto> positions;
        private Integer applicationCount;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime startAt;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime endAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PositionDto {
        private Long id;
        private String name;
    }
}
