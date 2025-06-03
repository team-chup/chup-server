package gsm.gsmjava.domain.position.repository;

import gsm.gsmjava.domain.position.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
