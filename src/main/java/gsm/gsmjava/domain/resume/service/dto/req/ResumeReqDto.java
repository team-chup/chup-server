package gsm.gsmjava.domain.resume.service.dto.req;

import gsm.gsmjava.domain.resume.type.ResumeType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResumeReqDto {
    private ResumeType type;
    private String url;
}
