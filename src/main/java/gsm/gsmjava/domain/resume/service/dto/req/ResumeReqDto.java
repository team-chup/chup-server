package gsm.gsmjava.domain.resume.service.dto.req;

import gsm.gsmjava.domain.resume.type.ResumeType;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private ResumeType type;
    @NotBlank
    private String url;
}
