package compartir.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public class PostUserInfo {
	private Long postId;
	private Long userId;
	private String userName;
	private String profile;
	
	public PostUserInfo(Long postId, Long userId, String userName, String profile) {
		this.postId = postId;
		this.userId = userId;
		this.userName = userName;
		this.profile = profile;
	}
}
