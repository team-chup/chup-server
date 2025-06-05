package gsm.gsmjava.domain.posting.repository;

import gsm.gsmjava.domain.posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    @Query("SELECT pg FROM Posting pg " +
            "JOIN FETCH pg.postingPositions pp " +
            "JOIN FETCH pp.position pn " +
            "WHERE pg.postingEndAt > :now " +
            "ORDER BY pg.createdAt DESC")
    List<Posting> queryNotEndFetchJoin(LocalDateTime now);

    @Query("SELECT pg FROM Posting pg " +
            "WHERE pg.id = :postingId AND pg.postingEndAt > :now " +
            "ORDER BY pg.createdAt DESC")
    Optional<Posting> queryNotEndById(LocalDateTime now, Long postingId);
}
