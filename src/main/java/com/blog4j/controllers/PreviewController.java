package com.blog4j.controllers;

import com.blog4j.servicees.ControllerService;
import com.blog4j.servicees.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/dashboard/preview", "/dashboard/preview.html"})
public class PreviewController {
  @Autowired
  private PostService postService;
  @Autowired
  private ControllerService controllerService;

  @GetMapping
  public String getPreview(Model model) {
    controllerService.addBlogProperties(model);
    model.addAttribute("blogPosts", postService.getAllPosts());
    return "preview";
  }
}
