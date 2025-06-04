package gsm.gsmjava.domain.user.service.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateInfoReqDto {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String studentNumber;
    @NotBlank
    private String phoneNumber;
}
