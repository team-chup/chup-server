package gsm.gsmjava.domain.position.service.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePositionReqDto {
    @NotBlank
    private String name;
}
