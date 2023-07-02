package compartir.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import compartir.project.domain.User;
import compartir.project.repository.JDBCUserRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@Transactional
class UserServiceTest {
	
	@Autowired UserService service;
	@Autowired JDBCUserRepository repository;

	@Test
	public void 로그인() {
//		given
		User user = new User("gildong4","3e2w1q","ginldong1@gmail.com","male","홍길동");
		service.addUser(user);
//		when
		Boolean result = service.login(user.getUserName(), user.getPassword());
//		then
		assertThat(result).isEqualTo(true);
	}
	
	@Test
	public void 계정추가() {
//		given
		User user = new User("gildong4","3e2w1q","ginldong1@gmail.com","male","홍길동");
//		when
		String result = service.addUser(user);
//		then
		log.info(result);
	}
	
	@Test
	public void 아이디검색() {
//		given
		User user = repository.save(new User("gildong4","3e2w1q","ginldong1@gmail.com","male","홍길동"));
//		when
		User result = service.findId(user.getUserId());
//		then
		log.info("[아이디검색] id >> {}",result.getUserId());
	}
	
	@Test
	public void 비밀번호검색() {
//		given
		User user = repository.save(new User("gildong4","3e2w1q","ginldong1@gmail.com","male","홍길동"));
//		when
		String result = service.searchPassword("gildong2",user.getEmail());
//		then
		log.info("[비밀번호 검색] password >> {}",result);
	}

}
