package gsm.gsmjava.infra.restapi.feign;

import gsm.gsmjava.infra.restapi.GoogleLoginService;
import gsm.gsmjava.infra.restapi.feign.client.GoogleOauthInfoFeignClient;
import gsm.gsmjava.infra.restapi.feign.dto.GoogleInfoResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static gsm.gsmjava.global.filter.JwtReqFilter.BEARER_PREFIX;

//@Service
@RequiredArgsConstructor
public class GoogleLoginFeignClientService implements GoogleLoginService {

    private final GoogleOauthInfoFeignClient googleOauthInfoFeignClient;

    public GoogleInfoResDto login(String accessToken) {
        try {
            return getInfo(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("google oauth 사용자 정보 요청 중 예외가 발생했습니다.");
        }
    }

    private GoogleInfoResDto getInfo(String accessToken) {
        return googleOauthInfoFeignClient.getInfo(BEARER_PREFIX + accessToken);
    }

}
