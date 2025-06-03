package gsm.gsmjava.domain.applicationresult.repository;

import gsm.gsmjava.domain.applicationresult.entity.ApplicationResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationResultRepository extends JpaRepository<ApplicationResult, Long> {
}
