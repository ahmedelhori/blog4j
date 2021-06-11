package com.blog4j.servicees;

import com.blog4j.entities.Post;
import com.blog4j.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
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
      generateIndex();
      generatePosts();
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  private void generateIndex() throws IOException {
    String basePath = "";
    Context context = new Context();
    context.setVariable("posts", postRepo.findByVisibleTrue());
    context.setVariable("basePath", basePath);
    addBlogProperties(context);
    String index = templateEngine.process("static/index.html", context);

    String fileName = path + "/index.html";
    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
      writer.write(index);
    }
  }

  private void generatePosts() throws IOException {
    List<Post> posts = postRepo.findByVisibleTrue();
    for (Post post : posts) {
      generatePost(post);
    }
  }

  private void addBlogProperties(Context context) {
    context.setVariable("blog_header", header);
    context.setVariable("blog_footer", footer);
    context.setVariable("blog_titel", title);
    context.setVariable("blog_domain", domain);
  }

  public boolean generatePost(Post post) {
    String basePath = "../";
    Context context = new Context();
    context.setVariable("post", post);
    context.setVariable("basePath", basePath);
    addBlogProperties(context);
    String postHtml = templateEngine.process("static/post.html", context);
    String fileName = path + "/post" + "/" + post.getTitle() + ".html";
    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
      writer.write(postHtml);
      generateIndex();
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  public boolean removePost(Post post) {
    String fileName = path + "/post" + "/" + post.getTitle() + ".html";
    try {
      Files.delete(Paths.get(fileName));
      generateIndex();
    } catch (IOException e) {
      return false;
    }
    return true;
  }
}