package gsm.gsmjava.domain.user.service;

import gsm.gsmjava.domain.portfolio.entity.Portfolio;
import gsm.gsmjava.domain.portfolio.repository.PortfolioRepository;
import gsm.gsmjava.domain.resume.entity.Resume;
import gsm.gsmjava.domain.resume.repository.ResumeRepository;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.domain.user.repository.UserRepository;
import gsm.gsmjava.domain.user.service.dto.req.SignUpReqDto;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SingUpService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final UserUtil userUtil;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public void signup(SignUpReqDto reqDto) {
        User currentUser = userUtil.getCurrentUser();

        currentUser.signup(reqDto);
        User updatedUser = userRepository.save(currentUser);

        Resume resume = (reqDto.getResume() == null)
                ? null
                : Resume.builder()
                    .user(updatedUser)
                    .name(reqDto.getResume().getName())
                    .url(reqDto.getResume().getUrl())
                    .build();
        resumeRepository.save(resume);

        Portfolio portfolio = (reqDto.getPortfolio() == null)
                ? null
                : Portfolio.builder()
                    .user(updatedUser)
                    .name(reqDto.getPortfolio().getName())
                    .url(reqDto.getPortfolio().getUrl())
                    .build();
        portfolioRepository.save(portfolio);
    }

}
