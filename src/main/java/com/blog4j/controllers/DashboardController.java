package com.blog4j.controllers;

import com.blog4j.servicees.ControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = {"/dashboard", "/dashboard.html"})
public class DashboardController {
  @Autowired
  private final ControllerService controllerService;

  @GetMapping
  public String getHandler(Model model) {
    controllerService.addBlogProperties(model);
    return "dashboard";
  }
}
