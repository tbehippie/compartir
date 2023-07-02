package compartir.project.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import compartir.project.domain.User;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@Transactional
class JDBCUserRepositoryTest {
	
	@Autowired JDBCUserRepository repository;

	@Test
	void 저장() {
//		given
		User user = new User("gildong3","q1w2e3","ginldong1@gmail.com","male","홍길동");
//		when
		repository.save(user);
//		then
		log.info("id >> {}",user.getUserId());
		assertThat(user.getUserId()).isNotNull();
	}
	
	@Test
	void 아이디검색() {
//		given
		User user = new User("gildong2","q1w2e3","ginldong1@gmail.com","male","홍길동");
		repository.save(user);
//		when
		User result = repository.findById(user.getUserId()).get();
//		then
		log.info("[아이디 검색] id >> {}",result.getUserId());
		log.info("real_name >> {}",result.getUserName());
		assertThat(result.getUserId()).isEqualTo(user.getUserId());
	}
	
	@Test
	void 유저아이디검색() {
//		given
		User user = new User("gildong2","q1w2e3","ginldong1@gmail.com","male","홍길동");
		repository.save(user);
//		when
		User result = repository.findByUserName(user.getUserName()).get();
//		then
		log.info("id >> {}",result.getUserId());
		log.info("real_name >> {}",result.getUserName());
		assertThat(result.getUserName()).isEqualTo(user.getUserName());
	}
	
	@Test
	void 유저이름검색() {
//		given
		User user = new User("gildong2","q1w2e3","ginldong1@gmail.com","male","홍길동");
		repository.save(user);
//		when
		User result = repository.findByRealName(user.getRealName()).get();
//		then
		log.info("id >> {}",result.getUserId());
		log.info("user_name >> {}",result.getUserName());
		assertThat(result.getRealName().trim()).isEqualTo(user.getRealName());
	}

	@Test
	void 전체검색() {
//		given
		int length = repository.findAll().size();
		User user = new User("gildong2","q1w2e3","ginldong1@gmail.com","male","홍길동");
		repository.save(user);
//		when
		List<User> result = repository.findAll();
//		then
		log.info("length >> {}",length);
		assertThat(result.size()).isEqualTo(length+1);
	}
	
	@Test
	void 비밀번호검색() {
//		given
		User user = new User("gildong2","q1w2e3","ginldong1@gmail.com","male","홍길동");
		repository.save(user);
//		when
		Optional<User> result = repository.findPassword(user.getUserName(),user.getEmail());
//		then
		log.info("result >> {}",result.get().getPassword());
	}
	
	@Test
	void 유저정보수정() {
//		given
		User user = new User("gildong2","e3w2q1","ginldong2@gmail.com","female","홍길동");
//		when
		User result = repository.updateUser(2L,user);
//		then
		log.info("id >> {}",result.getUserId());
		log.info("real_name >> {}",result.getUserName());
		assertThat(result.getUserName()).isEqualTo(user.getUserName());
	}

}
