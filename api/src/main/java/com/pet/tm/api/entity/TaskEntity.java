package com.pet.tm.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "TASK")
@Data
@AllArgsConstructor
@Builder
public class TaskEntity {

  @Id @GeneratedValue private Long id;

  @NotNull(message = "A Task must have a title")
  private String title;

  @NotNull(message = "A Task must have an associated description")
  private String description;

  private String status = "open";

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ASSIGNEE")
  private UserEntity assignee;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "TASK_ID", referencedColumnName = "ID")
  private List<CommentEntity> comments = new ArrayList<>();

  public TaskEntity() {}
}
