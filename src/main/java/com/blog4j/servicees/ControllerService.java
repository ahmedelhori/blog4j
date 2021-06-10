package com.blog4j.servicees;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ControllerService {
  @Value("${blog4j.blog.header}")
  private String header;
  @Value("${blog4j.blog.footer}")
  private String footer;
  @Value("${blog4j.blog.title}")
  private String title;
  @Value("${blog4j.blog.domain}")
  private String domain;

  public void addBlogProperties(Model model) {
    model.addAttribute("blog_header", header);
    model.addAttribute("blog_footer", footer);
    model.addAttribute("blog_titel", title);
    model.addAttribute("blog_domain", domain);
  }
}
