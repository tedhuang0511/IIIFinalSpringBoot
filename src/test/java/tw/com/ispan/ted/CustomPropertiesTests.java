package tw.com.ispan.ted;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomPropertiesTests {

	@Test
	void contextLoads() {
	}
	@Value("${password.valid.day}")
	private Integer d;

	@Value("${awsS3.secret}")
	String AWS_SECRET_KEY;
	@Test
	void mypropertiest(){
		System.out.println(d);
		System.out.println(AWS_SECRET_KEY);
	}




}
