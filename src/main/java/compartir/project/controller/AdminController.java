package compartir.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import compartir.project.domain.User;
import compartir.project.domain.UserPage;
import compartir.project.service.AdminService;
import compartir.project.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/users")
@Slf4j
public class AdminController {
	
	private final UserService userService;
	private final AdminService adminService;
	
	public AdminController(UserService userService, AdminService adminService) {
		this.userService = userService;
		this.adminService = adminService;
	}
	
	@GetMapping
	public String userList(@SessionAttribute User loginUser ,Model model,@RequestParam(defaultValue="1") int currentPage) {
		List<User> userList = userService.users();
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("userList", new UserPage(userList.size(), currentPage, 10, 10, userList));
		return "users/admin";
	}
	
	@GetMapping("/{userId}/block")
	public String userBlock(@SessionAttribute User loginUser, @PathVariable Long userId,Model model,@RequestParam(defaultValue="1") int currentPage) {
		adminService.blockUser(userId);
		List<User> userList = userService.users();
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("userList", new UserPage(userList.size(), currentPage, 10, 10, userList));
		return "users/admin";
	}
}
