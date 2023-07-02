package compartir.project.repository;

import java.util.List;
import java.util.Optional;

import compartir.project.domain.Admin;
import compartir.project.domain.User;

public interface UserRepository {
	public User save(User user);
	public Optional<User> findById(Long id);
	public Optional<User> findByUserName(String userName);
	public Optional<User> findByRealName(String realName);
	public Optional<User> findPassword(String userName,String email);
	public Optional<Admin> findAdmin(Long userId);
	public List<User> findAll();
	public User updateUser(Long updateId,User user);
	public void updateAuth(Integer auth,Long userId);
}
