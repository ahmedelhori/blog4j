package com.blog4j.controllers;

import com.blog4j.entities.Post;
import com.blog4j.servicees.ControllerService;
import com.blog4j.servicees.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = {"/dashboard/create", "/dashboard/create.html"})
public class CreateController {
  @Autowired
  private final PostService postService;

  @Autowired
  private final ControllerService controllerService;

  @GetMapping
  public String getHandler(Model model){
    controllerService.addBlogProperties(model);
    model.addAttribute("post", new Post());
    return "create";
  }

  @PostMapping
  public String postHandler(@Valid @ModelAttribute Post post, Errors errors, Model model){
    controllerService.addBlogProperties(model);
    System.out.println(errors.getAllErrors());
    if(errors.hasErrors()){
      return "create";
    }
    postService.submitPost(post);
    return "redirect:/dashboard/preview";
  }
}