package gsm.gsmjava.domain.postingfile.repository;

import gsm.gsmjava.domain.postingfile.entity.PostingFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingFileRepository extends JpaRepository<PostingFile, Long> {
}
