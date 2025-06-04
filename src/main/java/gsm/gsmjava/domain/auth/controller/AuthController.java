package gsm.gsmjava.domain.auth.controller;

import gsm.gsmjava.domain.auth.service.LoginService;
import gsm.gsmjava.domain.auth.service.dto.req.LoginReqDto;
import gsm.gsmjava.domain.auth.service.dto.res.AuthResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(
            @RequestBody LoginReqDto reqDto
    ) {
        AuthResDto response = loginService.login(reqDto);
        return ResponseEntity.ok(response);
    }

}
