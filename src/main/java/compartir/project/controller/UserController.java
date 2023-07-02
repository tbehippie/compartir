package compartir.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import compartir.project.domain.User;
import compartir.project.service.AdminService;
import compartir.project.service.PostService;
import compartir.project.service.UserService;

@Controller
public class UserController {
	
	private final UserService userService;
	private final PostService postService;
	private final AdminService adminService;
	
	public UserController(UserService userService, PostService postService, AdminService adminService) {
		this.userService = userService;
		this.postService = postService;
		this.adminService = adminService;
	}
	
	@GetMapping
	public String index(@SessionAttribute(required = false) User loginUser, Model model) {
		model.addAttribute("posts", postService.posts());
		model.addAttribute("postInfo", postService.postInfo());
		model.addAttribute("loginUser",loginUser);
		return "index";
	}
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
//	웹 브라우저가 쿠키를 지원하지 않을 때 쿠키 대신 URL을 통해 세션을 유지하는 방법인데, 
//	이를 없애기 위해서는 스프링 설정 파일(application.properties)에 다음과 같은 설정을 추가해주면 된다.
//	server.servlet.session.tracking-modes=cookie
	
	@PostMapping("/login")
	public String postLogin(String userName, String password, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(userService.login(userName, password)) {
			session.setAttribute("loginUser", userService.findUserName(userName));
			if(adminService.adminCheck(userService.findUserName(userName).getUserId()))
				return "redirect:/users";
			if(userService.findUserName(userName).getAuth() != 1)
				return "redirect:/noti/list";
			return "redirect:/";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String getLogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "login";
	}
	
	@GetMapping("/join")
	public String getJoin() {
		return "/user/join-form";
	}
	
	@PostMapping("/join")
	public String postJoin(User user,RedirectAttributes redirect) {
		String result = userService.addUser(user);
		redirect.addAttribute("result", result);
		return "redirect:/login";
	}
	
	@GetMapping("/pw")
	public String getPw() {
		return "/user/search-pwd";
	}
	
	@PostMapping("/pw")
	public String postPw(String userName, String email,Model model) {
		String result = userService.searchPassword(userName, email);
		model.addAttribute("result", result);
		return "/user/search-result";
	}
}
