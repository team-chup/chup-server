package gsm.gsmjava.domain.user.service.dto.res;

import gsm.gsmjava.domain.portfolio.service.dto.req.PortfolioReqDto;
import gsm.gsmjava.domain.resume.service.dto.req.ResumeReqDto;
import gsm.gsmjava.domain.user.type.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResDto {
    private String name;
    private String email;
    private String studentNumber;
    private String phoneNumber;
    private Authority authority;
    private ResumeReqDto resume;
    private PortfolioReqDto portfolio;
}
