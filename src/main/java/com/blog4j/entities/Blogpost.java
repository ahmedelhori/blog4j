package com.blog4j.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "tags")
@Entity
@Table(name = "blogposts")
public class Blogpost {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Boolean visible;
  private String title;
  private String author;
  private LocalDateTime createDate;
  private LocalDateTime editDate;
  private String content;
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  @JoinTable(name = "blogpost_tag",
    joinColumns = @JoinColumn(name = "blogpost"),
    inverseJoinColumns = @JoinColumn(name = "tag"))
  private List<BlogpostTag> tags = new ArrayList<>();

  public void addTag(BlogpostTag blogpostTag) {
    tags.add(blogpostTag);
    blogpostTag.addBlogpost(this);
  }

  public void removeTag(BlogpostTag blogpostTag) {
    tags.remove(blogpostTag);
    blogpostTag.removeBlogpost(this);
  }
}