package com.blog4j.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "posts")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @NotNull
  @Column(name = "visible")
  private Boolean visible = false;

  @NotBlank
  @Size(min = 1, max = 255)
  @Column(name = "title")
  private String title;

  @NotBlank
  @Size(min = 1, max = 255)
  @Column(name = "author")
  private String author;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "create_date")
  private LocalDate createDate = LocalDate.now();

  @NotNull
  @Column(name = "edit_date")
  private LocalDateTime editDate = LocalDateTime.now();

  @NotBlank
  @Size(min = 1, max = 20000)
  @Column(name = "content", length = 20000)
  private String content;

  @Size(min = 0, max = 1000)
  @Column(name = "tags_line", length = 1000)
  private String tagsLine;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "post_tag",
    joinColumns = @JoinColumn(name = "post"),
    inverseJoinColumns = @JoinColumn(name = "tag"))
  private Set<Tag> tags = new HashSet<>();

  public void addTag(Tag tag) {
    this.tags.add(tag);
    tag.addPost(this);
  }

  public void removeTag(Tag tag) {
    this.tags.remove(tag);
    tag.removePost(this);
  }
}