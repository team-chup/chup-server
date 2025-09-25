package gsm.gsmjava.domain.user.service;

import gsm.gsmjava.domain.portfolio.entity.Portfolio;
import gsm.gsmjava.domain.portfolio.repository.PortfolioRepository;
import gsm.gsmjava.domain.portfolio.service.dto.req.PortfolioReqDto;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.global.error.ExpectedException;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

        Portfolio portfolio = currentUser.getPortfolio() == null
                ? Portfolio.builder().build()
                : currentUser.getPortfolio();

        portfolio.update(reqDto.getUrl(), reqDto.getName(), currentUser);
        portfolioRepository.save(portfolio);
    }

    @Transactional
    public void delete() {
        User currentUser = userUtil.getCurrentUser();

        Portfolio portfolio = currentUser.getPortfolio();

        if (portfolio == null) {
            throw new ExpectedException("삭제할 포트폴리오가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        portfolioRepository.deleteById(portfolio.getId());
    }
}
