package gsm.gsmjava.domain.posting.service;

import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.posting.repository.PostingRepository;
import gsm.gsmjava.global.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePostingService {

    private final PostingRepository postingRepository;

    @Transactional
    public void delete(Long postingId) {
        Posting posting = postingRepository.findById(postingId).orElseThrow(
                () -> new ExpectedException("해당 공고는 존재하지 않습니다. id = " + postingId, HttpStatus.NOT_FOUND)
        );
        postingRepository.delete(posting);
    }

}
