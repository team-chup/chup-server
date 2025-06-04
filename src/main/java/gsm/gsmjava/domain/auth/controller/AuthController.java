package gsm.gsmjava.domain.auth.controller;

import gsm.gsmjava.domain.auth.service.LoginService;
import gsm.gsmjava.domain.auth.service.ReissueTokenService;
import gsm.gsmjava.domain.auth.service.dto.req.LoginReqDto;
import gsm.gsmjava.domain.auth.service.dto.res.AuthResDto;
import gsm.gsmjava.global.security.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static gsm.gsmjava.global.filter.JwtReqFilter.REFRESHTOKEN_HEADER;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final ReissueTokenService reissueTokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(
            @RequestBody LoginReqDto reqDto
    ) {
        AuthResDto response = loginService.login(reqDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(
            @RequestHeader(REFRESHTOKEN_HEADER) String refreshToken
    ) {
        TokenDto response = reissueTokenService.execute(refreshToken);
        return ResponseEntity.ok(response);
    }

}
