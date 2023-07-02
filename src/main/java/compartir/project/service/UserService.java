package compartir.project.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import compartir.project.domain.Admin;
import compartir.project.domain.User;
import compartir.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	private final UserRepository repository;
	
	public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	public Boolean login(String userName, String password) {
		Optional<User> user = repository.findByUserName(userName);
		if(user.isEmpty())
			return false;
		else if(user.get().getPassword().trim().equals(password))
			return true;
		return false;
	}
	
	public String addUser(User user) {
		if(repository.findByUserName(user.getUserName()).isPresent())
			return "해당 아이디가 이미 존재합니다.";
		repository.save(user);
		return "회원 가입 완료!";
	}

	public User findId(Long id) {
		Optional<User> result = repository.findById(id);
		if(result.isEmpty())
			return new User();
		return result.get();
	}

	public User findUserName(String userName) {
		Optional<User> result = repository.findByUserName(userName);
		if(result.isEmpty())
			return new User();
		return result.get();
	}

	public User findRealName(String realName) {
		Optional<User> result = repository.findByRealName(realName);
		if(result.isEmpty())
			return new User();
		return result.get();
	}

	public List<User> users() {
		return repository.findAll();
	}

	public User update(Long updateId,User user) {
		User updateUser = repository.updateUser(updateId,user);
		return updateUser;
	}
	
	public String updateProfileInService(MultipartFile profile,User user) throws IllegalStateException, IOException {
		if(!profile.getOriginalFilename().isEmpty()) {
			String path = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\image\\profile\\";// 저장 경로
			UUID uuid = UUID.randomUUID(); // 서버에 저장될 파일의 이름 중복 방지
			String name = uuid +"_"+profile.getOriginalFilename(); // 서버에 저장될 파일 이름
			
			profile.transferTo(new File(path, name)); // 실제 서버에 저장
			
			// 서버에 파일이 저장된 후 eclipse가 인식하기까지의 시간을 벌기 위해 thread를 잠시 정지합니다.
			// eclipse 가 refresh를 자동으로 하게 만들기 위해 window -> preferences -> general -> workspace -> 첫번째인 refresh~~를 체크 설정해줍니다.
//			System.out.println("Thread 정지");
			try {Thread.sleep(2500);} catch (Exception e) {}
//			System.out.println("Thread 재개");
			user.setProfile("/image/profile/"+name);
		}
		// db에 수정 정보로 들어가기 위해 임시로 만든 유저 : 업데이트할 유저 내용 정보중 프로필 경로만 설정한 것입니다.
		// 아래에 적혀있지 않은 setter들을 이용해 정보들을 모두 갖춘 유저 정보를 넘겨야합니다.
		return user.getProfile();
		// user.set~~~ 설정할 것
	}
	
	public String searchPassword(String userName, String email) {
		Optional<User> result = repository.findPassword(userName, email);
		if(result.isEmpty())
			return "해당 계정은 존재하지 않습니다.";
		return result.get().getPassword();
	}
}
