package gsm.gsmjava.domain.auth.service.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginReqDto {
    @NotBlank
    private String oauthToken;
}
