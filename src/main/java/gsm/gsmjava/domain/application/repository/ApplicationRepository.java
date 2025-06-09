package gsm.gsmjava.domain.application.repository;

import gsm.gsmjava.domain.application.entity.Application;
import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.user.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
           "FROM Application a WHERE a.posting.id = :postingId AND a.user.id = :userId")
    boolean isApplied(Long postingId, Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Application a WHERE a.user = :user AND a.posting = :posting")
    Optional<Application> findForUpdateByUserAndPosting(User user, Posting posting);

    @Query("SELECT a FROM Application a JOIN FETCH a.position pn JOIN FETCH a.posting pg LEFT OUTER JOIN a.applicationResult ar WHERE a.user = :user ORDER BY a.createdAt DESC")
    List<Application> findByUserFetchJoin(User user);

    @Query("SELECT a FROM Application a JOIN FETCH a.position pn JOIN FETCH a.posting pg LEFT OUTER JOIN a.applicationResult ar WHERE a.posting = :posting ORDER BY a.createdAt DESC")
    List<Application> findByPostingFetchJoin(Posting posting);

    @Query("SELECT a FROM Application a WHERE a.id IN (:ids) AND a.posting.id = :postingId ORDER BY a.createdAt DESC")
    List<Application> findByPostingIdAndIds(Long postingId, List<Long> ids);
}
