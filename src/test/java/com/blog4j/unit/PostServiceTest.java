package com.blog4j.unit;

import com.blog4j.entities.Post;
import com.blog4j.repositories.PostRepo;
import com.blog4j.servicees.GenerateBlogService;
import com.blog4j.servicees.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PostServiceTest {
  private Post post;
  private PostRepo mockPostRepo;
  private GenerateBlogService mockBlogService;
  private PostService postService;


  @BeforeEach
  public void setupPost() {
    post = new Post();
    post.setVisible(false);
    post.setTitle("Title title");
    post.setAuthor("Test Author");
    post.setCreateDate(LocalDate.now());
    post.setEditDate(LocalDateTime.now());
    post.setContent("Content");
    post.setTagsLine("test, blog test");
  }

  @BeforeEach
  public void setupPostRepo() {
    mockPostRepo = mock(PostRepo.class);
    mockBlogService = mock(GenerateBlogService.class);
    postService = new PostService(mockPostRepo, mockBlogService);
    when(mockPostRepo.save(any(Post.class))).thenReturn(post);
  }

  @Test
  public void createPost() {
    postService.submitPost(post);

    verify(mockPostRepo, times(1)).save(any(Post.class));
  }

  @Test
  public void createPostAndVerifyTagsSizeTest() {
    postService.submitPost(post);

    assertEquals(post.getTags().size(), 2, "Should include 2 tags");
  }

  @Test
  public void createPostAndVerifyTagsTest() {
    post.setTagsLine("test");

    postService.submitPost(post);

    assertEquals(post.getTags().iterator().next().getName(), "test", "Should be the same");
  }
}