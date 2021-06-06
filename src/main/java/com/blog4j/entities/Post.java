package com.blog4j.entities;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "posts")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @NotNull
  @Column(name = "visible")
  private Boolean visible;

  @Column(name = "title")
  private String title;

  @Column(name = "author")
  private String author;

  @NotNull
  @CreationTimestamp
  @Column(name = "create_date")
  private LocalDateTime createDate;

  @NotNull
  @UpdateTimestamp
  @Column(name = "edit_date")
  private LocalDateTime editDate;

  @NotBlank
  @Column(name = "content", length = 20000)
  private String content;

  @NotBlank
  @Column(name = "tags_line", length = 1000)
  private String tagsLine;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
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