package tw.com.ispan.ted.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class UserRepositoryTests {
	@Test
	void contextLoads() {
	}


	@Autowired
	private UserRepository userRepository;
	@Test
	@Transactional
	void testSelect(){
		var opt1 = userRepository.findById(10);
		if(opt1.isPresent()){
			System.out.println(opt1.get());
		}
		userRepository.findAll();
	}
	@Test
	@Transactional
	void testFindByAccount(){
		var res = userRepository.findByUserAccount("ted");
		if(!res.isEmpty()){
			System.out.println(res.get(0));
		}
	}

}
