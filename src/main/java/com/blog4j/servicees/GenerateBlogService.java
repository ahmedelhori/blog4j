package com.blog4j.servicees;

import com.blog4j.entities.Post;
import com.blog4j.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class GenerateBlogService {
  private final PostRepo postRepo;
  private final TemplateEngine templateEngine;

  private final String header;
  private final String footer;
  private final String title;
  private final String path;

  public GenerateBlogService(PostRepo postRepo, TemplateEngine templateEngine,
                             @Value("${blog4j.blog.header}") String header,
                             @Value("${blog4j.blog.footer}") String footer,
                             @Value("${blog4j.blog.title}") String title,
                             @Value("${blog4j.blog.path}") String path){
    this.postRepo = postRepo;
    this.templateEngine = templateEngine;
    this.header = header;
    this.footer = footer;
    this.title = title;
    this.path = path;
  }

  public void prepareEnv() throws IOException {
    Path dirPath = Path.of(path+"/post");
    if (!Files.isDirectory(dirPath)){
      Files.createDirectories(dirPath);
    }
    generateAssets();
		generateIndex();
  }

  private void generateAssets() throws IOException {
    String sourceZip = "asset/assets.zip";
    File destDir = new File(path);
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    try(InputStream inputStream = classloader.getResourceAsStream(sourceZip)){
      if(inputStream==null){
        throw new IOException("assets.zip not found");
      }
      try(ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
          File newFile = new File(destDir, zipEntry.getName());
          byte[] buffer = new byte[1024];
          if (zipEntry.isDirectory()) {
            if (!newFile.isDirectory() && !newFile.mkdirs()) {
              throw new IOException("Failed to create directory " + newFile);
            }
          } else {
            File parent = newFile.getParentFile();
            if (!parent.isDirectory() && !parent.mkdirs()) {
              throw new IOException("Failed to create directory " + parent);
            }

            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            int len;
            while ((len = zipInputStream.read(buffer)) > 0) {
              fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.close();
          }
        }
      }
    }
  }

  private void addBlogProperties(Context context) {
    context.setVariable("blog_header", header);
    context.setVariable("blog_footer", footer);
    context.setVariable("blog_title", title);
  }

  public void generateBlog() throws IOException {
    generateIndex();
    generatePosts();
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

  public boolean generatePost(Post post) throws IOException {
    String basePath = "../";
    Context context = new Context();
    context.setVariable("post", post);
    context.setVariable("basePath", basePath);
    addBlogProperties(context);
    String postHtml = templateEngine.process("static/post.html", context);
    String fileName = path + "/post" + "/" + post.getTitle() + ".html";
    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
      writer.write(postHtml);
      generateAssets();
      generateIndex();
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  public boolean removePost(Post post) {
    String fileName = path + "/post" + "/" + post.getTitle() + ".html";
    try {
      Files.deleteIfExists(Paths.get(fileName));
      generateIndex();
    } catch (IOException e) {
      return false;
    }
    return true;
  }
}
