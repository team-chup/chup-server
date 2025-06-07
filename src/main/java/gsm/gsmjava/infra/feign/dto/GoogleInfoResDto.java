package gsm.gsmjava.infra.feign.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleInfoResDto {
    private String email;

    public GoogleInfoResDto(String email) {
        this.email = email;
    }

    public GoogleInfoResDto() {
    }
}
