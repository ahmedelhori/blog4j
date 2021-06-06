package com.blog4j.repositories;

import com.blog4j.entities.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository<Post, Long> {
  @Override
  List<Post> findAll();
}