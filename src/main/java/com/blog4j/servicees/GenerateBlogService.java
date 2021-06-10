package com.blog4j.servicees;

import com.blog4j.entities.Post;
import com.blog4j.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.List;

@Service
public class GenerateBlogService {
  private final PostRepo postRepo;
  private final TemplateEngine templateEngine;

  private final String header;
  private final String footer;
  private final String title;
  private final String path;
  private final String domain;

  public GenerateBlogService(PostRepo postRepo, TemplateEngine templateEngine,
                             @Value("${blog4j.blog.header}") String header,
                             @Value("${blog4j.blog.footer}") String footer,
                             @Value("${blog4j.blog.title}") String title,
                             @Value("${blog4j.blog.path}") String path,
                             @Value("${blog4j.blog.domain}") String domain) {
    this.postRepo = postRepo;
    this.templateEngine = templateEngine;
    this.header = header;
    this.footer = footer;
    this.title = title;
    this.path = path;
    this.domain = domain;
  }

  public boolean generateBlog() {
    try {
      if (!verifyPath()) {
        return false;
      }
      generateIndex();
      generatePosts();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  private void generateIndex() throws IOException {
    String basePath = "";
    Context context = new Context();
    context.setVariable("posts", postRepo.findAll());
    context.setVariable("basePath", basePath);
    addBlogProperties(context);
    String index = templateEngine.process("static/index.html", context);

    String fileName = path + "/index.html";
    BufferedWriter writer = writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
    writer.write(index);

    writer.close();
  }

  private void generatePosts() throws IOException {
    String basePath = "../";
    Context context = new Context();
    List<Post> posts = postRepo.findAll();
    for (Post post : posts) {
      context.setVariable("post", post);
      context.setVariable("basePath", basePath);
      addBlogProperties(context);
      String postHtml = templateEngine.process("static/post.html", context);
      String fileName = path + "/post" + "/" + post.getTitle() + ".html";
      BufferedWriter writer = writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
      writer.write(postHtml);
      writer.close();
    }
  }

  private boolean verifyPath() {
    return new File(path).isDirectory();
  }

  private void addBlogProperties(Context context) {
    context.setVariable("blog_header", header);
    context.setVariable("blog_footer", footer);
    context.setVariable("blog_titel", title);
    context.setVariable("blog_domain", domain);
  }
}