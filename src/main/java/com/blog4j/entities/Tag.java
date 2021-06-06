package com.blog4j.entities;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
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
  @NotNull
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @NotNull
  @Column(name = "name")
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