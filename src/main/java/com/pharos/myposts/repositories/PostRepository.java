package com.pharos.myposts.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pharos.myposts.entities.Post;
import com.pharos.myposts.entities.User;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>{
	List<Post> findByUser(User user);
}
