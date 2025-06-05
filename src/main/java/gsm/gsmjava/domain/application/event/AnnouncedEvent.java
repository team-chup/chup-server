package gsm.gsmjava.domain.application.event;

import gsm.gsmjava.domain.applicationresult.type.ApplicationResultStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AnnouncedEvent {
    private Long postingId;
    private String email;
    private String name;
    private String companyName;
    private String position;
    private ApplicationResultStatus status;
    private String failedReason;
}
