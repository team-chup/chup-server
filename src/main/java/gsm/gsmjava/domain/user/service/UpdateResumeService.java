package gsm.gsmjava.domain.user.service;

import gsm.gsmjava.domain.resume.entity.Resume;
import gsm.gsmjava.domain.resume.repository.ResumeRepository;
import gsm.gsmjava.domain.resume.service.dto.req.ResumeReqDto;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.global.error.ExpectedException;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateResumeService {

    private final UserUtil userUtil;
    private final ResumeRepository resumeRepository;

    @Transactional
    public void update(ResumeReqDto reqDto) {
        User currentUser = userUtil.getCurrentUser();

        Resume resume = currentUser.getResume() == null
                ? Resume.builder().build()
                : currentUser.getResume();

        resume.update(reqDto.getUrl(), reqDto.getName(), currentUser);
        resumeRepository.save(resume);
    }

    @Transactional
    public void delete() {
        User currentUser = userUtil.getCurrentUser();

        Resume resume = currentUser.getResume();

        if (resume == null) {
            throw new ExpectedException("삭제할 이력서가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        resumeRepository.deleteById(resume.getId());
    }
}
