package gsm.gsmjava.domain.user.service.dto.req;

import gsm.gsmjava.domain.resume.service.dto.req.ResumeReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpReqDto {
    private String name;
    private String email;
    private String studentNumber;
    private String phoneNumber;
    private ResumeReqDto resume;
}
