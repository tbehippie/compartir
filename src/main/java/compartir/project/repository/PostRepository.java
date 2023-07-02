package compartir.project.repository;

import java.util.List;

import compartir.project.domain.Post;
import compartir.project.domain.PostUserInfo;

public interface PostRepository {
	Post save(Post post);
	void edit(Post post);
	void delete(Long postId);
	Post findById(Long postId);
	List<Post> findAll();
	List<Post> findAllByUserId(Long userId);
	List<PostUserInfo> findPostInfo();
}