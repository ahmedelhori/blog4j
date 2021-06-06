package com.blog4j.unit;

import com.blog4j.entities.Post;
import com.blog4j.entities.Tag;
import com.blog4j.repositories.PostRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ActiveProfiles("test")
@DataJpaTest
public class PostRepoTest {
  @Autowired
  private transient TestEntityManager testEntityManager;
  @Autowired
  private transient PostRepo postRepo;
  private transient Post post;

  @BeforeEach
  public void setupPost() {
    post = new Post();
    Tag tag1 = new Tag();
    Tag tag2 = new Tag();
    tag1.setName("test");
    tag2.setName("test");
    post.setVisible(false);
    post.setTitle("Title title");
    post.setAuthor("Test Author");
    post.setCreateDate(LocalDateTime.now());
    post.setEditDate(LocalDateTime.now());
    post.setContent(generateContent(5000));
    post.setTagsLine("test, PostTest");
    post.addTag(tag1);
    post.addTag(tag2);

    testEntityManager.persist(post);
    testEntityManager.flush();
  }

  @Test
  public void createPostTest() {
    List<Post> Posts = postRepo.findAll();

    assertEquals(Posts.size(), 1, "Should equal 1");
  }

  @Test
  public void postNullTest() {
    Post dbPost = postRepo.findAll().get(0);

    assertThat(dbPost).isNotNull();
  }

  @Test
  public void postVisibleTest() {
    Post dbPost = postRepo.findAll().get(0);

    assertEquals(dbPost.getVisible(), post.getVisible(), "Should be false");
  }

  @Test
  public void postTitleTest() {
    Post dbPost = postRepo.findAll().get(0);

    assertEquals(dbPost.getTitle(), post.getTitle(), "Should be post title");
  }

  @Test
  public void postAuthorTest() {
    Post dbPost = postRepo.findAll().get(0);

    assertEquals(dbPost.getAuthor(), post.getAuthor(), "Should be post author");
  }

  @Test
  public void postCreateDateTest() {
    Post dbPost = postRepo.findAll().get(0);

    assertEquals(dbPost.getCreateDate(), post.getCreateDate(), "Should be post creation date");
  }

  @Test
  public void postEditDateTest() {
    Post dbPost = postRepo.findAll().get(0);

    assertEquals(dbPost.getEditDate(), post.getEditDate(), "Should be post edited date");
  }

  @Test
  public void postContentTest() {
    post.setContent(generateContent(20000));

    testEntityManager.persist(post);
    testEntityManager.flush();
    Post dbPost = postRepo.findAll().get(0);

    assertEquals(dbPost.getContent(), post.getContent(), "Should be post content");
  }

  @Test
  public void postTagsLineTest() {
    post.setContent(generateContent(1000));

    testEntityManager.persist(post);
    testEntityManager.flush();
    Post dbPost = postRepo.findAll().get(0);

    assertEquals(dbPost.getContent(), post.getContent(), "Should be post tag line");
  }

  @Test
  public void postTagsTest() {
    Post dbPost = postRepo.findAll().get(0);
    assertThat(dbPost.getTags().toString()).isEqualTo(post.getTags().toString());
  }

  private String generateContent(int count) {
    StringBuilder content = new StringBuilder();
    for (int i = 0; i < count; i++) {
      content.append('z');
    }
    return content.toString();
  }
}