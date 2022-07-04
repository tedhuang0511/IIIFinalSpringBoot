package tw.com.ispan.ted;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

@SpringBootTest
public class MessagesSourceTests {
	@Test
	void contextLoads() {
	}
	
	//取得custom的properties1
	@Value("${password.valid.day}")
	private Integer psvd;
	//取得custom的properties2
	@Autowired
	private Environment env;
	@Test
	public void testCustomSettings() {
		System.out.println("password valid days="+psvd);
		Integer envPsvd = env.getProperty("password.valid.day",Integer.class);
		System.out.println(envPsvd);
	}
	
	
	@Autowired
	private MessageSource messageSource;
	@Test
	public void testI18n() {
		String test1 = messageSource.getMessage("login.failed", null, Locale.TAIWAN);
		System.out.println("test1="+test1);
		String test2 = messageSource.getMessage("product.id.required", new String[] {"Login"}, Locale.US);
		System.out.println("test2="+test2);
	}
}
