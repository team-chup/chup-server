package gsm.gsmjava.domain.position.repository;

import gsm.gsmjava.domain.position.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query("SELECT p FROM Position p WHERE p.id IN :ids")
    List<Position> queryIds(List<Long> ids);

    boolean existsByName(String name);
}
