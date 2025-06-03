package gsm.gsmjava.domain.postingposition.repository;

import gsm.gsmjava.domain.postingposition.entity.PostingPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingPositionRepository extends JpaRepository<PostingPosition, Long> {
}
