package gsm.gsmjava.infra.client;

import gsm.gsmjava.infra.dto.GoogleInfoResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "GoogleOauthInfoClient", url = "${oauth.google.info-url}")
public interface GoogleOauthInfoFeignClient {
    @GetMapping
    GoogleInfoResDto getInfo(
        @RequestHeader("Authorization") String accessToken
    );
}
