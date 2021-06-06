package com.blog4j.unit;

import com.blog4j.servicees.ControllerService;
import com.blog4j.servicees.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private PostService postService;
  @MockBean
  private ControllerService controllerService;

  @BeforeEach
  public void setup(){
    when(postService.getAllPosts()).thenReturn(new ArrayList<>());
  }


  @Test
  public void getRequestTest() throws Exception {
    mockMvc.perform(get("/"))
      .andDo(print())
      .andExpect(status().isOk());
  }
}
