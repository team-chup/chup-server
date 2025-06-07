package gsm.gsmjava.infra.restapi.feign.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GoogleFeignConfig {
    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(3000, 3000);
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 2);
    }
}
