package gsm.gsmjava.domain.user.service;

import gsm.gsmjava.domain.resume.entity.Resume;
import gsm.gsmjava.domain.resume.service.dto.req.ResumeReqDto;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.domain.user.service.dto.res.UserInfoResDto;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserUtil userUtil;

    @Transactional(readOnly = true)
    public UserInfoResDto getInfo() {
        User currentUser = userUtil.getCurrentUser();
        Resume resume = currentUser.getResume();
        return UserInfoResDto.builder()
                .name(currentUser.getName())
                .email(currentUser.getEmail())
                .phoneNumber(currentUser.getPhoneNumber())
                .studentNumber(currentUser.getStudentNumber())
                .authority(currentUser.getAuthority())
                .resume(
                        resume == null ? null :
                        ResumeReqDto.builder()
                            .type(resume.getType())
                            .url(resume.getUrl())
                            .name(resume.getName())
                            .build()
                )
                .build();
    }

}
