package com.blog4j.unit;

import com.blog4j.repositories.PostRepo;
import com.blog4j.servicees.GenerateBlogService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("test")
public class GenerateBlogServiceTest {
  @Autowired
  private TemplateEngine templateEngine;
  private PostRepo postRepo;
  private GenerateBlogService generator;

  @TempDir
  File tempDir;

  @BeforeEach
  public void setup(){
    postRepo = mock(PostRepo.class);
    generator = new GenerateBlogService(postRepo, templateEngine, "test", "test", "test", tempDir.getPath(), "http://localhost:8080");
  }

  @Test
  public void verifyPathTest() {
    assertTrue(generator.generateBlog());
  }
}
