package gsm.gsmjava.domain.posting.service.dto.req;

import gsm.gsmjava.domain.posting.type.CompanyLocation;
import gsm.gsmjava.domain.posting.type.EmploymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreatePostingReqDto {
    @NotBlank
    private String companyName;
    @NotBlank
    private String companyDescription;
    @NotNull
    private CompanyLocation companyLocation;
    @NotNull
    private EmploymentType employmentType;
    @NotNull
    private List<Long> positions;
    @NotNull
    private List<PostingFile> files;
    @NotNull
    private LocalDateTime startAt;
    @NotNull
    private LocalDateTime endAt;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PostingFile {
        private String name;
        private String url;
    }
}
