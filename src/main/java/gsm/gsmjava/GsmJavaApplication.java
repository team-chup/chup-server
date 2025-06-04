package gsm.gsmjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GsmJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GsmJavaApplication.class, args);
	}

}
