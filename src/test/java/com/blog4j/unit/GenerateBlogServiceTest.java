package com.blog4j.unit;

import com.blog4j.entities.Post;
import com.blog4j.entities.Tag;
import com.blog4j.repositories.PostRepo;
import com.blog4j.servicees.GenerateBlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.TemplateEngine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class GenerateBlogServiceTest {
  @TempDir
  File tempDir;
  @Autowired
  private TemplateEngine templateEngine;
  private PostRepo postRepo;
  private GenerateBlogService generator;

  @BeforeEach
  public void setup() {
    Post post = new Post();
    post.setVisible(false);
    post.setTitle("Title title");
    post.setAuthor("Test Author");
    post.setCreateDate(LocalDate.now());
    post.setEditDate(LocalDateTime.now());
    post.setContent("Test content");
    post.setTagsLine("test, PostTest");
    postRepo = mock(PostRepo.class);
    List<Post> posts = new ArrayList<>();
    posts.add(post);
    when(postRepo.findByVisibleTrue()).thenReturn(posts);
    generator = new GenerateBlogService(postRepo, templateEngine, "test", "test", "test", tempDir.getPath(), "http://localhost:8080");
  }

  @Test
  public void prepareEnvTest() throws IOException {
    generator.prepareEnv();

    File dir = new File(tempDir.getPath()+"/post");
    assertTrue(dir.isDirectory());
  }

  @Test
  public void generateAssetsTest() throws IOException {
    generator.prepareEnv();

    File dir = new File(tempDir.getPath()+"/styles/main.css");
    assertTrue(dir.isFile());
  }

  @Test
  public void generateIndexTest() throws IOException {
    generator.prepareEnv();
    generator.generateBlog();

    File dir = new File(tempDir.getPath()+"/index.html");
    assertTrue(dir.isFile());
  }

  @Test
  public void generatePostTest() throws IOException {
    generator.prepareEnv();
    generator.generateBlog();

    File dir = new File(tempDir.getPath()+"/post/Title title.html");
    assertTrue(dir.isFile());
  }
}