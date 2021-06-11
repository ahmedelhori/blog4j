package com.blog4j.repositories;

import com.blog4j.entities.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends CrudRepository<Post, Long> {
  @Override
  List<Post> findAll();
}