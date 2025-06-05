package gsm.gsmjava.domain.application.service.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyReqDto {
    @NotNull
    private Long positionId;
}
