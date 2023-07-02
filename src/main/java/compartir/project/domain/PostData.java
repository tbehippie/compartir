package compartir.project.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostData {
	public Long userId;
	public MultipartFile postImage;
	public String postContent;
	
	public PostData(Long userId ,MultipartFile postImage, String postContent) {
		super();
		this.userId = userId;
		this.postImage = postImage;
		this.postContent = postContent;
	}
	
	public PostData(MultipartFile postImage, String postContent) {
		this.postImage = postImage;
		this.postContent = postContent;
	}
}
