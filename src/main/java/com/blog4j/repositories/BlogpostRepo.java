package com.blog4j.repositories;

import com.blog4j.entities.Blogpost;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlogpostRepo extends CrudRepository<Blogpost, Long> {
  @Override
  List<Blogpost> findAll();
}