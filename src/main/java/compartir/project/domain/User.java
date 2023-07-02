package compartir.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class User {
	private Long userId;
	private String userName;
	private String password;
	private String email;
	private String gender;
	private String realName;
	private String profile;
	private Integer auth;
	
	public User(String userName, String password, String email, String gender, String realName) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.realName = realName;
	}
}
