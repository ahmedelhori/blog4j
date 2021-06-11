package com.blog4j.controllers;

import com.blog4j.servicees.ControllerService;
import com.blog4j.servicees.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = {"/dashboard"})
public class ModifyController {
  private final PostService postService;
  private final ControllerService controllerService;

  @PostMapping("/editpost")
  public String editHandler(@RequestParam(name = "id") Long id, Model model) {
    controllerService.addBlogProperties(model);
    model.addAttribute("post", postService.getPost(id));
    return "create";
  }

  @PostMapping("/publishpost")
  public String publishHandler(@RequestParam(name = "id") Long id, Model model) {
    controllerService.addBlogProperties(model);
    postService.togglePublishPost(id);
    return "redirect:/dashboard/preview";
  }

  @PostMapping("/deletepost")
  public String deleteHandler(@RequestParam(name = "id") Long id, Model model) {
    controllerService.addBlogProperties(model);
    postService.deletePost(id);
    return "redirect:/dashboard/preview";
  }
}
