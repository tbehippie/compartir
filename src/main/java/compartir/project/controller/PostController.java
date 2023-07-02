package compartir.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import compartir.project.domain.Post;
import compartir.project.domain.PostData;
import compartir.project.domain.User;
import compartir.project.domain.UserPage;
import compartir.project.service.AdminService;
import compartir.project.service.PostService;
import compartir.project.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/post")
public class PostController{

	private final PostService postService;
	private final UserService userService;
	private final AdminService adminService;
	
	public PostController(PostService postService, UserService userService, AdminService adminService) {
		this.postService = postService;
		this.userService = userService;
		this.adminService = adminService;
	}
	
	@GetMapping("/create")
	public String createPostView(@SessionAttribute User loginUser, Model model) {
		User user = userService.findId(loginUser.getUserId());
		model.addAttribute(user);
		model.addAttribute("loginUser",loginUser);
		return "post/post-create";
	}
	
	@PostMapping("/create")
	public String createPost(@SessionAttribute User loginUser, @RequestParam(name="postImage", required = false) MultipartFile file, @RequestParam(name="postContent", required = false) String content, RedirectAttributes redirect) throws IllegalStateException, IOException {
		PostData postForm = new PostData(loginUser.getUserId(),file, content);
		Post savedPost = postService.create(postForm);
		redirect.addAttribute("postId", savedPost.getPostId());
		
		return "redirect:/post/{postId}";
	}
	
	@GetMapping("/{postId}")
	public String postView(@SessionAttribute(required=false) User loginUser, @PathVariable Long postId, Model model) {
		Post foundPost = postService.findPostById(postId);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("post", foundPost);
		model.addAttribute("writer",userService.findId(foundPost.getUserId()));
		
		if(loginUser != null)
			model.addAttribute("auth", adminService.adminCheck(loginUser.getUserId()));
		
		return "post/post";
	}
	
	@PostMapping("/{postId}/del")
	public String deletePost(@SessionAttribute User loginUser, @PathVariable Long postId,@RequestParam(defaultValue = "1") int currentPage,Model model) {
		postService.deletePost(postId);
		if(adminService.adminCheck(loginUser.getUserId())) {
			List<User> userList = userService.users();
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("userList", new UserPage(userList.size(), currentPage, 10, 10, userList));
			return "users/admin";
		}
		// 보여줄 뷰 입력
		return "redirect:/";
	}
	
	/**
	 * 보안 문제 존재
	 * - GET 방식이므로 sessionAttribute를 이용하지 않으면 로그인하지 않고도 접근이 가능한 문제 발생
	 * - 세션 확인하는 기능(후순위) : sessionAttribute에 required=true로 인해 접근 불가
	 * @param postId
	 * @param model
	 * @return
	 */
	@GetMapping("/{postId}/edit")
	public String editPostView(@SessionAttribute User loginUser, @PathVariable Long postId, Model model) {
		Post foundPost = postService.findPostById(postId);
		model.addAttribute(foundPost);
		model.addAttribute("loginUser", loginUser);
		return "post/post-edit";
	}
	
	@PostMapping("/{postId}/edit")
	public String editPost(@PathVariable Long postId, @RequestParam(name="postContent", required=false) String modifiedContent, @RequestParam(name="postImage", required=false) MultipartFile modifiedImage, Model model) throws IllegalStateException, IOException {
		PostData postForm = new PostData(modifiedImage, modifiedContent);
		postService.editPost(postForm, postId);
		log.info("게시물 수정 완료");
		return "redirect:/post/{postId}";
	}
}
