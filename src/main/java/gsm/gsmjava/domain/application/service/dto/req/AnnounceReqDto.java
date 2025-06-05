package gsm.gsmjava.domain.application.service.dto.req;

import gsm.gsmjava.domain.applicationresult.type.ApplicationResultStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnnounceReqDto {
    @NotNull
    private ApplicationResultStatus status;
    @Nullable
    private String failedReason;
}
