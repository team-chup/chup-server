package gsm.gsmjava.domain.posting.service.dto.req;

import gsm.gsmjava.domain.posting.type.CompanyLocation;
import gsm.gsmjava.domain.posting.type.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreatePostingReqDto {
    private String companyName;
    private String companyDescription;
    private CompanyLocation companyLocation;
    private EmploymentType employmentType;
    private List<Long> positions;
    private List<PostingFile> files;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PostingFile {
        private String name;
        private String url;
    }
}
