package com.blog4j.unit;

import com.blog4j.entities.Post;
import com.blog4j.entities.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {
  private Post post;
  private Tag tag;

  @BeforeEach
  public void setup() {
    post = new Post();
    tag = new Tag();
  }

  @Test
  public void testAddTag() {
    post.addTag(tag);

    assertTrue(post.getTags().contains(tag), "Should be the same");
  }

  @Test
  public void testAddPost() {
    post.addTag(tag);

    assertTrue(tag.getPosts().contains(post), "Should be the same");
  }

  @Test
  public void testRemoveTag() {
    post.addTag(tag);
    post.removeTag(tag);

    assertFalse(post.getTags().contains(tag), "Should be empty");
  }

  @Test
  public void testRemovePost() {
    post.addTag(tag);
    post.removeTag(tag);
    assertFalse(tag.getPosts().contains(post), "Should be empty");
  }

  @Test
  public void testPostToString() {
    post.addTag(tag);

    assertDoesNotThrow(post::toString, "Should output the string representation of Post");
  }

  @Test
  public void testPostHashCode() {
    post.addTag(tag);

    assertDoesNotThrow(post::hashCode, "Should output the hash code of Post");
  }

  @Test
  public void testTagToString() {
    post.addTag(tag);

    assertDoesNotThrow(post::toString, "Should output the string representation of Post");
  }

  @Test
  public void testTagHashCode() {
    post.addTag(tag);

    assertDoesNotThrow(post::hashCode, "Should output the hash code of Post");
  }
}