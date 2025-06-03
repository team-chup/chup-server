package gsm.gsmjava.domain.posting.repository;

import gsm.gsmjava.domain.posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositingRepository extends JpaRepository<Posting, Long> {
}
