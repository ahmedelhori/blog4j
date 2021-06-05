package com.blog4j.unit;

import com.blog4j.entities.Blogpost;
import com.blog4j.entities.BlogpostTag;
import com.blog4j.repositories.BlogpostRepo;
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
public class BlogpostRepoTest {
  @Autowired
  private transient TestEntityManager testEntityManager;
  @Autowired
  private transient BlogpostRepo blogpostRepository;
  private transient Blogpost blogpost;

  @BeforeEach
  public void setupBlogpost() {
    blogpost = new Blogpost();
    BlogpostTag tag1 = new BlogpostTag();
    BlogpostTag tag2 = new BlogpostTag();
    tag1.setName("test");
    tag2.setName("blog");
    blogpost.setVisible(false);
    blogpost.setTitle("Title title");
    blogpost.setAuthor("Test Author");
    blogpost.setCreateDate(LocalDateTime.now());
    blogpost.setEditDate(LocalDateTime.now());
    blogpost.setContent(generateContent(5000));
    blogpost.addTag(tag1);
    blogpost.addTag(tag2);
  }

  @Test
  public void createBlogpostTest() {
    testEntityManager.persist(blogpost);
    List<Blogpost> blogposts = blogpostRepository.findAll();
    assertEquals(blogposts.size(), 1, "Should equal 1");
  }

  @Test
  public void blogPostNullTest() {
    testEntityManager.persist(blogpost);
    Blogpost dbBlogpost = blogpostRepository.findAll().get(0);
    assertThat(dbBlogpost).isNotNull();
  }

  @Test
  public void blogPostVisibleTest() {
    testEntityManager.persist(blogpost);
    Blogpost dbBlogpost = blogpostRepository.findAll().get(0);
    assertEquals(dbBlogpost.getVisible(), blogpost.getVisible(), "Should be false");
  }

  @Test
  public void blogPostTitleTest() {
    testEntityManager.persist(blogpost);
    Blogpost dbBlogpost = blogpostRepository.findAll().get(0);
    assertEquals(dbBlogpost.getTitle(), blogpost.getTitle(), "Should be blog title");
  }

  @Test
  public void blogPostAuthorTest() {
    testEntityManager.persist(blogpost);
    Blogpost dbBlogpost = blogpostRepository.findAll().get(0);
    assertEquals(dbBlogpost.getAuthor(), blogpost.getAuthor(), "Should be blog author");
  }

  @Test
  public void blogPostCreateDateTest() {
    testEntityManager.persist(blogpost);
    Blogpost dbBlogpost = blogpostRepository.findAll().get(0);
    assertEquals(dbBlogpost.getCreateDate(), blogpost.getCreateDate(), "Should be blog creation date");
  }

  @Test
  public void blogPostEditDateTest() {
    testEntityManager.persist(blogpost);
    Blogpost dbBlogpost = blogpostRepository.findAll().get(0);
    assertEquals(dbBlogpost.getEditDate(), blogpost.getEditDate(), "Should be blog edited date");
  }

  @Test
  public void blogPostContentTest() {
    blogpost.setContent(generateContent(20000));
    testEntityManager.persist(blogpost);

    testEntityManager.persist(blogpost);
    testEntityManager.flush();
    Blogpost dbBlogpost = blogpostRepository.findAll().get(0);

    assertEquals(dbBlogpost.getContent(), blogpost.getContent(), "Should be blog content");
  }

  @Test
  public void blogPostTagsTest() {
    testEntityManager.persist(blogpost);
    Blogpost dbBlogpost = blogpostRepository.findAll().get(0);
    assertEquals(dbBlogpost.getTags(), blogpost.getTags(), "Should be blog tags");
  }

  private String generateContent(int count) {
    StringBuilder content = new StringBuilder();
    for (int i = 0; i < count; i++) {
      content.append('z');
    }
    return content.toString();
  }
}