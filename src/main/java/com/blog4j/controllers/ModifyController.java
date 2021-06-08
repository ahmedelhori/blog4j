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
@RequestMapping(value = {"/dashboard"})
public class ModifyController {
  private final PostService postService;
  private final ControllerService controllerService;

  @GetMapping("/editpost/{id}")
  public String editHandler(@PathVariable("id") long id, Model model) {
    controllerService.addBlogProperties(model);
    model.addAttribute("post", postService.getPost(id));
    return "create";
  }

  @GetMapping("/publishpost/{id}")
  public String publishHandler(@PathVariable("id") long id, Model model) {
    controllerService.addBlogProperties(model);
    postService.publishPost(id);
    model.addAttribute("posts", postService.getAllPosts());
    return "preview";
  }

  @GetMapping("/deletepost/{id}")
  public String deleteHandler(@PathVariable("id") long id, Model model) {
    controllerService.addBlogProperties(model);
    postService.deletePost(id);
    model.addAttribute("posts", postService.getAllPosts());
    return "preview";
  }
}
