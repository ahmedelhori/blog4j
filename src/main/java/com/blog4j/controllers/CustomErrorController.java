package com.blog4j.controllers;

import com.blog4j.servicees.ControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class CustomErrorController implements ErrorController {
  private final ControllerService controllerService;

  @RequestMapping(value = {"/dashboard/error", "/dashboard/error.html"})
  public String handleError(HttpServletRequest request, Model model) {
    controllerService.addBlogProperties(model);
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());
      model.addAttribute("statusCode", statusCode);
    }
    return "error";
  }
}
