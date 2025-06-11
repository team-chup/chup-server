package gsm.gsmjava.domain.auth.service;

import gsm.gsmjava.domain.auth.entity.RefreshToken;
import gsm.gsmjava.domain.auth.repository.RefreshTokenRepository;
import gsm.gsmjava.domain.auth.service.dto.req.LoginReqDto;
import gsm.gsmjava.domain.auth.service.dto.res.AuthResDto;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.domain.user.repository.UserRepository;
import gsm.gsmjava.global.error.ExpectedException;
import gsm.gsmjava.global.security.jwt.TokenGenerator;
import gsm.gsmjava.global.security.jwt.dto.TokenDto;
import gsm.gsmjava.infra.restapi.GoogleLoginService;
import gsm.gsmjava.infra.restapi.feign.GoogleLoginFeignClientService;
import gsm.gsmjava.infra.restapi.feign.dto.GoogleInfoResDto;
import gsm.gsmjava.infra.restapi.resttemplate.GoogleLoginRestClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final GoogleLoginService googleLoginService;
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public AuthResDto login(LoginReqDto reqDto) {
        GoogleInfoResDto infoDto = googleLoginService.login(reqDto.getOauthToken());

        validGSMLogin(infoDto.getEmail());

        User user = getUserOrNew(infoDto);
        TokenDto tokenDto = tokenGenerator.generateToken(String.valueOf(user.getId()));

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(user.getId())
                .token(tokenDto.getRefreshToken())
                .expirationTime(tokenDto.getRefreshTokenExp())
                .build();
        refreshTokenRepository.save(refreshToken);

        return AuthResDto.builder()
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .authority(user.getAuthority())
                .build();
    }

    private User getUserOrNew(GoogleInfoResDto infoDto) {
        return userRepository
                .findByOauthEmail(infoDto.getEmail())
                .orElseGet(
                        () -> userRepository.save(User.of(infoDto.getEmail()))
                );
    }

    private void validGSMLogin(String email) {
        if (!email.endsWith("@gsm.hs.kr")) {
            throw new ExpectedException("요청한 이메일이 GSM 학생용 이메일이 아닙니다.", HttpStatus.BAD_REQUEST);
        }
    }

}
