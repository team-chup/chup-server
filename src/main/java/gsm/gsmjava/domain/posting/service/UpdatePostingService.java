package gsm.gsmjava.domain.posting.service;

import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.posting.repository.PostingRepository;
import gsm.gsmjava.domain.posting.service.dto.req.UpdatePostingReqDto;
import gsm.gsmjava.global.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static gsm.gsmjava.global.cache.CacheConstant.POSTING_LIST_CACHE;

@Service
@RequiredArgsConstructor
public class UpdatePostingService {

    private final PostingRepository postingRepository;

    @Transactional
    @CacheEvict(value = POSTING_LIST_CACHE, cacheManager = "cacheManager")
    public void update(Long postingId, UpdatePostingReqDto reqDto) {
        Posting posting = postingRepository.findById(postingId).orElseThrow(
                () -> new ExpectedException("해당 공고는 존재하지 않습니다. id = " + postingId, HttpStatus.BAD_REQUEST)
        );
        posting.update(reqDto);
        postingRepository.save(posting);
    }

}
