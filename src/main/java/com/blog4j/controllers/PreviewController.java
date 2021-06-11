package com.blog4j.controllers;

import com.blog4j.servicees.ControllerService;
import com.blog4j.servicees.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = {"/dashboard/preview", "/dashboard/preview.html"})
public class PreviewController {
  private final PostService postService;
  private final ControllerService controllerService;

  @GetMapping
  public String getPreview(Model model) {
    controllerService.addBlogProperties(model);
    model.addAttribute("posts", postService.getAllPosts());
    return "preview";
  }
}
