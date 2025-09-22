package gsm.gsmjava.domain.user.service;

import gsm.gsmjava.domain.portfolio.entity.Portfolio;
import gsm.gsmjava.domain.portfolio.repository.PortfolioRepository;
import gsm.gsmjava.domain.portfolio.service.dto.req.PortfolioReqDto;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatePortfolioService {

    private final UserUtil userUtil;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public void update(PortfolioReqDto reqDto) {
        User currentUser = userUtil.getCurrentUser();

        Portfolio portfolio = currentUser.getPortfolio();
        portfolio.update(reqDto.getUrl(), reqDto.getName());
        portfolioRepository.save(portfolio);
    }

}
