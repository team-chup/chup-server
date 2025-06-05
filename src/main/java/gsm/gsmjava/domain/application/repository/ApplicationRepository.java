package gsm.gsmjava.domain.application.repository;

import gsm.gsmjava.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
           "FROM Application a WHERE a.posting.id = :postingId AND a.user.id = :userId")
    boolean isApplied(Long postingId, Long userId);
}
