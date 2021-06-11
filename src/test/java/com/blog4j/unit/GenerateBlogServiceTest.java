package com.blog4j.unit;

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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

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
    postRepo = mock(PostRepo.class);
    generator = new GenerateBlogService(postRepo, templateEngine, "test", "test", "test", tempDir.getPath(), "http://localhost:8080");
  }

  @Test
  public void verifyPathTest() {
    assertTrue(generator.generateBlog());
  }
}
