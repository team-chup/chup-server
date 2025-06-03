package gsm.gsmjava.domain.resume.repository;

import gsm.gsmjava.domain.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
