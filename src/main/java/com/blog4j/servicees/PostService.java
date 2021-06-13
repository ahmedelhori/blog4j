package com.blog4j.servicees;

import com.blog4j.entities.Post;
import com.blog4j.entities.Tag;
import com.blog4j.repositories.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
  private final PostRepo postRepository;
  private final GenerateBlogService generateBlogService;

  public void submitPost(Post post) {
    if(post.getId()!=null) {
      Optional<Post> oldPost = postRepository.findById(post.getId());
      generateBlogService.removePost(oldPost.get());
    }
    Post preparedPost = preparePost(post);
    postRepository.save(preparedPost);
    if (preparedPost.getVisible()) {
      try {
        generateBlogService.generatePost(preparedPost);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
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

  public void togglePublishPost(long id) {
    Post post = postRepository.findById(id).get();
    if (!post.getVisible()) {
      post.setVisible(true);
      postRepository.save(post);
      try {
        generateBlogService.generatePost(post);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      post.setVisible(false);
      postRepository.save(post);
      generateBlogService.removePost(post);
    }
  }

  public void deletePost(long id) {
    Post post = postRepository.findById(id).get();
    postRepository.deleteById(id);
    generateBlogService.removePost(post);
  }
}