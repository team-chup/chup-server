package gsm.gsmjava.domain.posting.event;

import gsm.gsmjava.domain.posting.type.CompanyLocation;
import gsm.gsmjava.domain.posting.type.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CreatePostingEvent {
    private Long postingId;
    private String companyName;
    private String companyDescription;
    private CompanyLocation companyLocation;
    private EmploymentType employmentType;
    private List<String> positions;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
