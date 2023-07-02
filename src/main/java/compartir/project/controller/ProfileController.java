package compartir.project.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import compartir.project.domain.User;
import compartir.project.service.AdminService;
import compartir.project.service.PostService;
import compartir.project.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/profile")
@Slf4j
public class ProfileController {
	
	private final UserService userService;
	private final PostService postService;
	private final AdminService adminService;
	
	public ProfileController(UserService userService, PostService postService, AdminService adminService) {
		this.userService = userService;
		this.postService = postService;
		this.adminService = adminService;
	}
	
	@GetMapping("/{userId}")
	public String profile(@SessionAttribute(required = false) User loginUser, @PathVariable Long userId,Model model) {
		List<Post> posts = postService.userPosts(userId);
		
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("profile", userService.findId(userId));
		model.addAttribute("posts", posts);
		// admin 정보 값
		if(loginUser != null) {
			model.addAttribute("auth", adminService.adminCheck(loginUser.getUserId()));
		}
		
		return "user/profile";
	}
	
	@GetMapping("/edit")
	public String getEdit(@SessionAttribute User loginUser,Model model) {
		model.addAttribute("loginUser", loginUser);
		return "user/profile-edit";
	}
	
	@PostMapping("/edit")
	public String postEdit(@SessionAttribute User loginUser, @RequestParam(name="profileIcon", required=false) MultipartFile profileIcon, User updateUser, HttpServletRequest request, RedirectAttributes redirect) throws IllegalStateException, IOException {
		String profile = userService.updateProfileInService(profileIcon,loginUser);
		updateUser.setProfile(profile);
		request.getSession().setAttribute("loginUser", userService.update(loginUser.getUserId(), updateUser));
		redirect.addAttribute("userId", loginUser.getUserId());
		return "redirect:/profile/{userId}";
	}
	
	@GetMapping("/search")
	public String searchProfile(@SessionAttribute(required = false) User loginUser, String search, Model model) {
		User result = userService.findUserName(search);
		List<Post> posts = postService.userPosts(result.getUserId());
		model.addAttribute("loginUser",loginUser);
		if(result.getUserId() == null) {
			model.addAttribute("posts", postService.posts());
			model.addAttribute("postInfo", postService.postInfo());
			return "index";
		}

		model.addAttribute("profile", userService.findId(result.getUserId()));
		model.addAttribute("posts", posts);
		if(loginUser != null)
			model.addAttribute("auth", adminService.adminCheck(loginUser.getUserId()));	
		return "/user/profile";
	}
}

// user목록에서 admin 제외
