package compartir.project.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import compartir.project.domain.Post;
import compartir.project.domain.PostData;
import compartir.project.domain.PostUserInfo;
import compartir.project.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostService {
	private final PostRepository repository;
	public PostService(PostRepository repository) {
		this.repository=repository;
	}
	
	/**
	 * 게시글 저장 기능
	 * @param postForm - html에서 넘어온 정보
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public Post create(PostData postForm) throws IllegalStateException, IOException {
		MultipartFile uploadedFile = postForm.getPostImage();
		Post post = new Post();
		if(!uploadedFile.getOriginalFilename().isEmpty()) {
			String path = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\image\\post\\";
			UUID uuid = UUID.randomUUID(); // 서버에 저장될 파일의 이름 중복 방지
			String name = uuid +"_"+uploadedFile.getOriginalFilename();
			post.setPostImage("/image/post/"+name);
			uploadedFile.transferTo(new File(path, name)); // 물리적 위치에 저장하기
		}
		// DB에 저장할 post 임시파일 생성
		post.setUserId(postForm.getUserId());
		post.setPostContent(postForm.getPostContent());
		post.setDate(LocalDate.now().toString());
		Post savedPost = repository.save(post); // DB에 파일 저장
		
		// 서버에서 파일이 인식되는 시간 벌기
		try {Thread.sleep(2500);} catch (Exception e) {}
		
		return savedPost;
	}
	
	public Post findPostById(Long postId) {
		return repository.findById(postId);
	}
	
	public void deletePost(Long postId) {
		repository.delete(postId);
	}
	
	/**
	 * 문제점
	 * - 이미지 파일 교체시 기존에 존재하던 이미지 파일이 서버에 그대로 남아있음.
	 * @param postForm
	 * @param postId
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void editPost(PostData postForm, Long postId) throws IllegalStateException, IOException {
		Post post = repository.findById(postId);
		if(!postForm.getPostImage().isEmpty()) {
			MultipartFile uploadedFile = postForm.getPostImage();
			String path = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\image\\post\\";
			
			UUID uuid = UUID.randomUUID(); // 서버에 저장될 파일의 이름 중복 방지
			String name = uuid +"_"+uploadedFile.getOriginalFilename();
			
			uploadedFile.transferTo(new File(path, name)); // 물리적 위치에 저장하기
			
			post.setPostImage("/image/post/"+name);
		}
		if(!postForm.getPostContent().isEmpty()) {
			post.setPostContent(postForm.getPostContent());
		}
		post.setDate(LocalDate.now().toString());
		repository.edit(post);
	}
	
	public List<Post> posts(){
		return repository.findAll();
	}
	
	public List<Post> userPosts(Long userId) {
		return repository.findAllByUserId(userId);
	}
	
	public List<PostUserInfo> postInfo(){
		return repository.findPostInfo();
	}
	
}
