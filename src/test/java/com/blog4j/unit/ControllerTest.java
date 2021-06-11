package com.blog4j.unit;

import com.blog4j.entities.Post;
import com.blog4j.servicees.ControllerService;
import com.blog4j.servicees.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ControllerTest {
  private Post post;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private PostService postService;
  @MockBean
  private ControllerService controllerService;

  @BeforeEach
  public void setup() {
    post = new Post();
    post.setAuthor("Test Author");
    post.setCreateDate(LocalDate.now());
    post.setTitle("Title title");
    post.setContent("Content");
    post.setTagsLine("test, blog test");

    when(postService.getAllPosts()).thenReturn(new ArrayList<>());
    when(postService.getPost(1L)).thenReturn(post);
  }

  @Test
  public void getDashboardTest() throws Exception {
    mockMvc.perform(get("/dashboard"))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  public void getPreviewTest() throws Exception {
    mockMvc.perform(get("/dashboard/preview"))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  public void error404Test() throws Exception {
    mockMvc.perform(get("/dashboard/site-doesnt-exist"))
      .andDo(print())
      .andExpect(status().is4xxClientError());
  }

  @Test
  public void getCreateTest() throws Exception {
    mockMvc.perform(get("/dashboard/create"))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  public void postCreateTest() throws Exception {
    mockMvc.perform(post("/dashboard/create")
      .contentType("application/json"))
      .andExpect(status().isOk());
  }

  @Test
  public void postCreatePostTest() throws Exception {

    mockMvc.perform(post("/dashboard/create")
      .flashAttr("post", post))
      .andExpect(status().isFound());
  }

  @Test
  public void getPostParam() throws Exception {
    mockMvc.perform(get("/dashboard/post/1"))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  public void editPost() throws Exception {
    postService.submitPost(post);

    mockMvc.perform(post("/dashboard/publishpost")
      .param("id", "1"))
      .andExpect(status().isFound());
  }

  @Test
  public void deletePost() throws Exception {
    postService.submitPost(post);

    mockMvc.perform(post("/dashboard/publishpost")
      .param("id", "1"))
      .andExpect(status().isFound());
  }

  @Test
  public void publishPost() throws Exception {
    postService.submitPost(post);

    mockMvc.perform(post("/dashboard/publishpost")
      .param("id", "1"))
      .andExpect(status().isFound());
  }
}
