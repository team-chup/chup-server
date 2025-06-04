package gsm.gsmjava.domain.auth.service;

import gsm.gsmjava.domain.auth.service.dto.req.LoginReqDto;
import gsm.gsmjava.domain.auth.service.dto.res.AuthResDto;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.domain.user.repository.UserRepository;
import gsm.gsmjava.global.security.jwt.TokenGenerator;
import gsm.gsmjava.global.security.jwt.dto.TokenDto;
import gsm.gsmjava.infra.GoogleLoginFeignClientService;
import gsm.gsmjava.infra.dto.GoogleInfoResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final GoogleLoginFeignClientService googleLoginService;
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;

    @Transactional
    public AuthResDto login(LoginReqDto reqDto) {
        GoogleInfoResDto infoDto = googleLoginService.login(reqDto.getOauthToken());

        User user = getUserOrNew(infoDto);
        TokenDto tokenDto = tokenGenerator.generateToken(String.valueOf(user.getId()));

        return AuthResDto.builder()
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .authority(user.getAuthority())
                .build();
    }

    private User getUserOrNew(GoogleInfoResDto infoDto) {
        return userRepository
                .findByOauthEmail(infoDto.getEmail())
                .orElse(
                        userRepository.save(User.of(infoDto.getEmail()))
                );
    }

}
