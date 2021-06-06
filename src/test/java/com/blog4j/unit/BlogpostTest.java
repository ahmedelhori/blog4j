package com.blog4j.unit;

import com.blog4j.entities.Blogpost;
import com.blog4j.entities.BlogpostTag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlogpostTest {

  @Test
  public void testAddTag(){
    Blogpost blogpost = new Blogpost();
    BlogpostTag tag = new BlogpostTag();

    blogpost.addTag(tag);

    assertEquals(blogpost.getTags().get(0), tag, "Should be the same");
  }

  @Test
  public void testAddBlogpost(){
    Blogpost blogpost = new Blogpost();
    BlogpostTag tag = new BlogpostTag();

    blogpost.addTag(tag);

    assertEquals(tag.getBlogposts().get(0), blogpost, "Should be the same");
  }

  @Test
  public void testRemoveTag(){
    Blogpost blogpost = new Blogpost();
    BlogpostTag tag = new BlogpostTag();

    blogpost.addTag(tag);
    blogpost.removeTag(tag);

    assertTrue(blogpost.getTags().isEmpty(), "Should be empty");
  }

  @Test
  public void testRemoveBlogpost(){
    Blogpost blogpost = new Blogpost();
    BlogpostTag tag = new BlogpostTag();

    blogpost.addTag(tag);
    blogpost.removeTag(tag);

    assertTrue(tag.getBlogposts().isEmpty(), "Should be empty");
  }

  @Test
  public void testBlogpostToString(){
    Blogpost blogpost = new Blogpost();
    BlogpostTag tag = new BlogpostTag();

    blogpost.addTag(tag);

    assertNotNull(blogpost.toString(), "Should exclude tags");
  }

  @Test
  public void testBlogpostTagToString(){
    Blogpost blogpost = new Blogpost();
    BlogpostTag tag = new BlogpostTag();

    blogpost.addTag(tag);

    assertNotNull(tag.toString(), "Should exclude blogposts");
  }
}
