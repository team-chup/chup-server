package gsm.gsmjava.domain.user.controller;

import gsm.gsmjava.domain.user.service.SingUpService;
import gsm.gsmjava.domain.user.service.dto.req.SignUpReqDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final SingUpService singUpService;

    @PostMapping("/signup")
    public ResponseEntity<Void> singUp(
        @RequestBody @Valid SignUpReqDto reqDto
    ) {
        singUpService.signup(reqDto);
        return ResponseEntity.ok().build();
    }

}
