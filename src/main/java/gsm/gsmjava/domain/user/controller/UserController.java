package gsm.gsmjava.domain.user.controller;

import gsm.gsmjava.domain.user.service.SingUpService;
import gsm.gsmjava.domain.user.service.UserInfoService;
import gsm.gsmjava.domain.user.service.dto.req.SignUpReqDto;
import gsm.gsmjava.domain.user.service.dto.res.UserInfoResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final SingUpService singUpService;
    private final UserInfoService userInfoService;

    @PostMapping("/signup")
    public ResponseEntity<Void> singUp(
        @RequestBody @Valid SignUpReqDto reqDto
    ) {
        singUpService.signup(reqDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResDto> info() {
        UserInfoResDto response = userInfoService.getInfo();
        return ResponseEntity.ok(response);
    }

}
