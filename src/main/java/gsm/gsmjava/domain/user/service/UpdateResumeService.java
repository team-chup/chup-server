package gsm.gsmjava.domain.user.service;

import gsm.gsmjava.domain.resume.entity.Resume;
import gsm.gsmjava.domain.resume.repository.ResumeRepository;
import gsm.gsmjava.domain.resume.service.dto.req.ResumeReqDto;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
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

        Resume resume = currentUser.getResume();
        resume.update(reqDto.getType(), reqDto.getUrl(), reqDto.getName());
        resumeRepository.save(resume);
    }

}
