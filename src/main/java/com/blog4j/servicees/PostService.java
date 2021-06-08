package com.blog4j.servicees;

import com.blog4j.entities.Post;
import com.blog4j.entities.Tag;
import com.blog4j.repositories.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
  @Autowired
  private final PostRepo postRepository;

  public void submitPost(Post post) {
    Post preparedPost = preparePost(post);
    postRepository.save(preparedPost);
  }

  private Post preparePost(Post post) {
    post.setEditDate(LocalDateTime.now());
    String[] splitTags = post.getTagsLine().split(",", -1);
    for (String splitTag : splitTags) {
      Tag tag = new Tag();
      tag.setName(splitTag);
      post.addTag(tag);
    }
    return post;
  }

  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  public Post getPost(Long id) {
    return postRepository.findById(id).get();
  }
}