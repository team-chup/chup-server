package gsm.gsmjava.domain.auth.service.dto.res;

import gsm.gsmjava.domain.user.type.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResDto {
    private String accessToken;
    private String refreshToken;
    private Authority authority;
}
