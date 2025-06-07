package gsm.gsmjava.infra.restapi.feign.client;

import gsm.gsmjava.infra.restapi.feign.config.GoogleFeignConfig;
import gsm.gsmjava.infra.restapi.feign.dto.GoogleInfoResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "GoogleOauthInfoClient",
        url = "https://www.googleapis.com",
        configuration = GoogleFeignConfig.class
)
public interface GoogleOauthInfoFeignClient {
    @GetMapping(
        value = "/oauth2/v1/userinfo",
        consumes = "application/json"
    )
    GoogleInfoResDto getInfo(
        @RequestHeader("Authorization") String accessToken
    );
}
