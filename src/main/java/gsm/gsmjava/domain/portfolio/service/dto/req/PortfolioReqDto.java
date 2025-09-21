package gsm.gsmjava.domain.portfolio.service.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioReqDto {
    @NotBlank
    private String name;
    @NotBlank
    private String url;
}
