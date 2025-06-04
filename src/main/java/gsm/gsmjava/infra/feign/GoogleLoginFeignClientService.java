package gsm.gsmjava.infra.feign;

import gsm.gsmjava.infra.feign.client.GoogleOauthInfoFeignClient;
import gsm.gsmjava.infra.feign.dto.GoogleInfoResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static gsm.gsmjava.global.filter.JwtReqFilter.BEARER_PREFIX;

@Service
@RequiredArgsConstructor
public class GoogleLoginFeignClientService {

    private final GoogleOauthInfoFeignClient googleOauthInfoFeignClient;

    public GoogleInfoResDto login(String accessToken) {
        try {
            return getInfo(accessToken);
        } catch (Exception e) {
            throw new RuntimeException("google oauth 사용자 정보 요청 중 예외가 발생했습니다.");
        }
    }

    private GoogleInfoResDto getInfo(String accessToken) {
        return googleOauthInfoFeignClient.getInfo(BEARER_PREFIX + accessToken);
    }

}
