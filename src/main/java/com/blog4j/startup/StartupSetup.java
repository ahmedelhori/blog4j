package com.blog4j.startup;

import com.blog4j.servicees.GenerateBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class StartupSetup implements ApplicationListener<ApplicationReadyEvent>{
  private final GenerateBlogService generateBlogService;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    try {
      generateBlogService.prepareEnv();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
