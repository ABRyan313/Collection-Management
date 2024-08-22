package in.sp.itransition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "in.sp.itransition")
public class DtreasuresApplication {

	public static void main(String[] args) {
		SpringApplication.run(DtreasuresApplication.class, args);
		System.out.println("HI");
	}

}
