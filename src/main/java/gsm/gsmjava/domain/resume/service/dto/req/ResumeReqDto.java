package gsm.gsmjava.domain.resume.service.dto.req;

import gsm.gsmjava.domain.resume.type.ResumeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeReqDto {
    @NotBlank
    private String name;
    @NotNull
    private ResumeType type;
    @NotBlank
    private String url;
}
