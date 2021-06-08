package com.blog4j.controllers;

import com.blog4j.servicees.ControllerService;
import com.blog4j.servicees.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = {"/dashboard/post"})
public class PostController {
  private final PostService postService;
  private final ControllerService controllerService;

  @GetMapping("/{id}")
  public String getHandler(@PathVariable("id") long id, Model model) {
    controllerService.addBlogProperties(model);
    model.addAttribute("post", postService.getPost(id));
    return "post";
  }
}
