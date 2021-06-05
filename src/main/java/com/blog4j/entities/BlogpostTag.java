package com.blog4j.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "blogposts")
@Entity
@Table(name = "tags")
public class BlogpostTag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
  private List<Blogpost> blogposts = new ArrayList<>();

  public void addBlogpost(Blogpost blogpost) {
    blogposts.add(blogpost);
  }

  public void removeBlogpost(Blogpost blogpost) {
    blogposts.remove(blogpost);
  }
}