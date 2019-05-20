package com.pharos.myposts.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharos.myposts.entities.Post;
import com.pharos.myposts.entities.User;
import com.pharos.myposts.repositories.PostRepository;

@Service
public class PostService {

	@Autowired
	private UserService userService;

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
    private EntityManager em;

	public void savePost(String userName, String content, boolean isPrivate) {
		User user = userService.findByUsername(userName);
		if (user != null) {
			Post post = new Post(content, isPrivate, user);
			postRepository.save(post);
		}
	}

	public List<Post> findUserPosts(String userName) {
		User user = userService.findByUsername(userName);
		return user != null ? postRepository.findByUser(user) : new ArrayList<>();
	}

	public List<Post> searchPublicPosts(String word) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Post> cq = cb.createQuery(Post.class);
		String keywords = "%" + Arrays.stream(word.split(" ")).map(keyword -> keyword.toUpperCase())
				.collect(Collectors.joining("%")) + "%";
		
		Root<Post> post = cq.from(Post.class);
		Predicate contentPredicate = cb.like(cb.upper(post.get("content")), keywords);
		Predicate statusPredicate = cb.equal(post.get("isPrivate"), false);
		cq.where(statusPredicate, contentPredicate);
		
		TypedQuery<Post> query = em.createQuery(cq);
		return query.getResultList();
	}
}
