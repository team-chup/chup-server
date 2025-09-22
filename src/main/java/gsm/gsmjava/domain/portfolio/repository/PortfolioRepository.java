package gsm.gsmjava.domain.portfolio.repository;

import gsm.gsmjava.domain.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
