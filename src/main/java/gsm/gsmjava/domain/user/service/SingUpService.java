package gsm.gsmjava.domain.user.service;

import gsm.gsmjava.domain.resume.entity.Resume;
import gsm.gsmjava.domain.resume.repository.ResumeRepository;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.domain.user.repository.UserRepository;
import gsm.gsmjava.domain.user.service.dto.req.SignUpReqDto;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SingUpService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final UserUtil userUtil;

    @Transactional
    public void signup(SignUpReqDto reqDto) {
        User currentUser = userUtil.getCurrentUser();

        currentUser.signup(reqDto);
        userRepository.save(currentUser);

        Resume resume = Resume.builder()
                .type(reqDto.getResume().getType())
                .url(reqDto.getResume().getUrl())
                .build();
        resumeRepository.save(resume);
    }

}
