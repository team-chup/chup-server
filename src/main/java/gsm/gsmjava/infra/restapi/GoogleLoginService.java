package gsm.gsmjava.infra.restapi;

import gsm.gsmjava.infra.restapi.feign.dto.GoogleInfoResDto;

public interface GoogleLoginService {
    GoogleInfoResDto login(String accessToken);
}
