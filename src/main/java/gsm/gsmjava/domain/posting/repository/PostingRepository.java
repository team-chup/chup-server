package gsm.gsmjava.domain.posting.repository;

import gsm.gsmjava.domain.posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    @Query("SELECT pg FROM Posting pg " +
            "JOIN FETCH PostingPosition pp " +
            "JOIN FETCH Position pn " +
            "WHERE pg.postingEndAt > :now " +
            "ORDER BY pg.createdAt DESC")
    List<Posting> queryNotEnd(LocalDateTime now);
}
