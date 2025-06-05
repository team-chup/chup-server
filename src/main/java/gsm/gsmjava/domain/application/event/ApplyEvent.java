package gsm.gsmjava.domain.application.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ApplyEvent {
    private Long applicationId;
    private Long positingId;
    private String companyName;
    private String positionName;
    private String applicantName;
    private String applicantEmail;
    private String applicantPhoneNumber;
    private LocalDateTime applicantDate;
}
