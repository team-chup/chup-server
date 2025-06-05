package gsm.gsmjava.domain.posting.service.dto.req;

import gsm.gsmjava.domain.posting.type.CompanyLocation;
import gsm.gsmjava.domain.posting.type.EmploymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UpdatePostingReqDto {
    @NotBlank
    private String companyDescription;
    @NotBlank
    private String companyName;
    @NotNull
    private CompanyLocation companyLocation;
    @NotNull
    private EmploymentType employmentType;
    @NotNull
    private LocalDateTime startAt;
    @NotNull
    private LocalDateTime endAt;
}
