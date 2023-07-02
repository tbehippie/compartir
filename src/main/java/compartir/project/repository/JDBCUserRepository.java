package compartir.project.repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import compartir.project.domain.Admin;
import compartir.project.domain.User;

@Repository
public class JDBCUserRepository implements UserRepository{
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCUserRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public RowMapper<User> userMapper() {
		return (ResultSet rs,int rowNum) -> {
			User user = new User(rs.getString("user_name"), rs.getString("password"),rs.getString("email"),rs.getString("gender"),rs.getString("real_name"));
			user.setProfile(rs.getString("profile")); 
			user.setAuth(rs.getInt("auth"));
			user.setUserId(rs.getLong("user_id"));
			return user;
		};
	}
	
	public RowMapper<Admin> adminMapper() {
		return (ResultSet rs,int rowNum) -> {
			Admin admin = new Admin(rs.getLong("admin_id"),rs.getLong("user_id"));
			return admin;
		};
	}

	@Override
	public User save(User user) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("user_id");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user_name", user.getUserName());
		map.put("password", user.getPassword());
		map.put("email", user.getEmail());
		map.put("gender", user.getGender());
		map.put("real_name", user.getRealName());
		map.put("profile", "/image/profile/profile.png");
		map.put("auth", 1);
		Number result = insert.executeAndReturnKey(new MapSqlParameterSource(map));
		user.setUserId(result.longValue());
		return user;
	}

	@Override
	public Optional<User> findById(Long id) {
		List<User> userList = jdbcTemplate.query("select * from users where user_id = ?",userMapper(), id);
		return userList.stream().findAny();
	}

	@Override
	public Optional<User> findByUserName(String userName) {
		List<User> userList = jdbcTemplate.query("select * from users where user_name like ?",userMapper(), userName);
		return userList.stream().findAny();
	}

	@Override
	public Optional<User> findByRealName(String realName) {
		List<User> userList = jdbcTemplate.query("select * from user_table where real_name like ?",userMapper(), realName);
		return userList.stream().findAny();
	}
	
	@Override
	public Optional<Admin> findAdmin(Long userId) {
		return jdbcTemplate.query("select * from admin where user_id = ?", adminMapper(),userId).stream().findAny();
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("select * from users",userMapper());
	}

	@Override
	public Optional<User> findPassword(String userName, String email) {
		List<User> userList = jdbcTemplate.query("select * from users where (user_name like ?) and (email like ?);", userMapper(),userName,email);
		return userList.stream().findAny();
	}

	@Override
	public User updateUser(Long updateId,User user) {
		jdbcTemplate.update("update users set user_name = ?, password = ?, email = ?, gender = ?, real_name = ?, profile = ? where user_id = ?",
				user.getUserName(),user.getPassword(),user.getEmail(),user.getGender(),user.getRealName(),user.getProfile(),updateId);
		return findById(updateId).get();
	}

	@Override
	public void updateAuth(Integer auth,Long userId) {
		jdbcTemplate.update("update users set auth = ? where user_id = ?",auth,userId);
	}

	
}
