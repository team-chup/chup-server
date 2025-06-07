package gsm.gsmjava.infra.restapi.resttemplate;

import gsm.gsmjava.infra.restapi.GoogleLoginService;
import gsm.gsmjava.infra.restapi.feign.dto.GoogleInfoResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static gsm.gsmjava.global.filter.JwtReqFilter.AUTHORIZATION_HEADER;
import static gsm.gsmjava.global.filter.JwtReqFilter.BEARER_PREFIX;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleLoginRestClientService implements GoogleLoginService {

    private static final RestTemplate restTemplate = new RestTemplate();

    public GoogleInfoResDto login(String accessToken) {
        try {
            return getInfo(accessToken);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException("google oauth 사용자 정보 요청 중 예외가 발생했습니다.");
        }
    }

    private GoogleInfoResDto getInfo(String accessToken) {
        log.info("Google OAuth accessToken: {}", accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION_HEADER, BEARER_PREFIX + accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<GoogleInfoResDto> response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v1/userinfo",
                HttpMethod.GET,
                entity,
                GoogleInfoResDto.class
        );

        return response.getBody();
    }
}
