package com.pharos.myposts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pharos.myposts.services.PostService;
import com.pharos.myposts.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/validate")
	public String validateUser(@RequestParam("username") String userName, @RequestParam("password") String password,
			ModelMap model) {
		if (userService.valiateUser(userName, password)) {
			model.addAttribute("posts", postService.findUserPosts(userService.getLoggedUsername()));
			return "createPost";
		} else {
			return "login";
		}
	}
}