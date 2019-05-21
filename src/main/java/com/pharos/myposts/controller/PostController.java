package com.pharos.myposts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pharos.myposts.entities.Post;
import com.pharos.myposts.exceptions.InvalidApiParameterException;
import com.pharos.myposts.services.PostService;
import com.pharos.myposts.services.UserService;

@Controller
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@PostMapping("/savePost")
	public String savePost(@RequestParam("content") String content,
			@RequestParam(value = "publicPost", required = false) String[] publicPost, ModelMap model) {
		boolean isPrivate = publicPost == null ? true : false;
		postService.savePost(userService.getLoggedUsername(), content, isPrivate);
		// Get user posts
		String username = userService.getLoggedUsername();
		if (username != null) {
			model.addAttribute("posts", postService.findUserPosts(username));
			return "createPost";
		} else {
			return "login";
		}
	}

	@GetMapping("/search")
	@ResponseBody
	public List<Post> searchPublicPosts(@RequestParam("word") String word) throws InvalidApiParameterException {
		if (word == null || word.trim().isEmpty()) {
			throw new InvalidApiParameterException("Search string cann't be null/empty/only whitespaces");
		}
		return postService.searchPublicPosts(word);
	}
}
