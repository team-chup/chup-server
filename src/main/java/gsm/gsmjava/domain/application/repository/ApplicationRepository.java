package gsm.gsmjava.domain.application.repository;

import gsm.gsmjava.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
