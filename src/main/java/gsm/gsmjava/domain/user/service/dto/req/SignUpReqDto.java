package gsm.gsmjava.domain.user.service.dto.req;

import gsm.gsmjava.domain.portfolio.service.dto.req.PortfolioReqDto;
import gsm.gsmjava.domain.resume.service.dto.req.ResumeReqDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpReqDto {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String studentNumber;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private ResumeReqDto resume;
    @NotNull
    private PortfolioReqDto portfolio;
}
