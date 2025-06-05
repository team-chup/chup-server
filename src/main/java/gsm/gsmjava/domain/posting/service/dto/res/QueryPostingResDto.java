package gsm.gsmjava.domain.posting.service.dto.res;

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
public class QueryPostingResDto {
    private Integer count;
    private List<PostingDto> postings;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PostingDto {
        private Long id;
        private String companyName;
        private CompanyLocation companyLocation;
        private EmploymentType employmentType;
        private List<PositionDto> positions;
        private Integer applicationCount;
        private LocalDateTime createdAt;
        private LocalDateTime startAt;
        private LocalDateTime endAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PositionDto {
        private Long id;
        private String name;
    }
}
