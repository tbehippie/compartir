package compartir.project.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Post {
	private Long userId;
	private String date;
	private Long postId;
	private String postImage;
	private String postContent;
	
	public Post() {}
	
	public Post(Long userId, String date, Long postId, String postImage, String postContent) {
		this.postId = postId;
		this.postImage = postImage;
		this.postContent = postContent;
		this.userId = userId;
		this.date = date;
	}
	
	
}
