package com.blog4j.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(exclude = "posts")
@EqualsAndHashCode(exclude = "posts")
@Entity
@Table(name = "tags")
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @NotBlank
  @Size(min = 1, max = 255)
  @Column(name = "name", length = 255)
  private String name;

  @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
  private Set<Post> posts = new HashSet<>();

  public void addPost(Post post) {
    posts.add(post);
  }

  public void removePost(Post post) {
    posts.remove(post);
  }
}