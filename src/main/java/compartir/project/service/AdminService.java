package compartir.project.service;

import org.springframework.stereotype.Service;

import compartir.project.repository.UserRepository;

@Service
public class AdminService {

	private final UserRepository userRepository;

	public AdminService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public boolean adminCheck(Long userId) {
		return userRepository.findAdmin(userId).isPresent();
	}

	public void blockUser(Long userId) {
		Integer result = userRepository.findById(userId).get().getAuth() == 1 ? 0 : 1;
		userRepository.updateAuth(result,userId);
	}
}
