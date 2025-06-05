package gsm.gsmjava.domain.application.repository;

import gsm.gsmjava.domain.application.entity.Application;
import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
           "FROM Application a WHERE a.posting.id = :postingId AND a.user.id = :userId")
    boolean isApplied(Long postingId, Long userId);

    boolean existsByUserAndPosting(User user, Posting posting);
}
