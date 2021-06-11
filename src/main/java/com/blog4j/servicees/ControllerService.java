package com.blog4j.servicees;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ControllerService {
  private final String header;
  private final String footer;
  private final String title;
  private final String domain;

  public ControllerService(@Value("${blog4j.blog.header}") String header,
                           @Value("${blog4j.blog.footer}") String footer,
                           @Value("${blog4j.blog.title}") String title,
                           @Value("${blog4j.blog.path}") String path,
                           @Value("${blog4j.blog.domain}") String domain) {
    this.header = header;
    this.footer = footer;
    this.title = title;
    this.domain = domain;
  }

  public void addBlogProperties(Model model) {
    model.addAttribute("blog_header", header);
    model.addAttribute("blog_footer", footer);
    model.addAttribute("blog_titel", title);
    model.addAttribute("blog_domain", domain);
  }
}
