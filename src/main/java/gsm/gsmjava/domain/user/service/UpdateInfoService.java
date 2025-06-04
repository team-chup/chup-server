package gsm.gsmjava.domain.user.service;

import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.domain.user.repository.UserRepository;
import gsm.gsmjava.domain.user.service.dto.req.UpdateInfoReqDto;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateInfoService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;

    @Transactional
    public void update(UpdateInfoReqDto reqDto) {
        User currentUser = userUtil.getCurrentUser();
        currentUser.update(reqDto);
        userRepository.save(currentUser);
    }

}
