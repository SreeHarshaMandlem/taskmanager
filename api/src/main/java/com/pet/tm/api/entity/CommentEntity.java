package com.pet.tm.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "COMMENT")
@Data
@AllArgsConstructor
@Builder
public class CommentEntity {

  @Id @GeneratedValue private Long id;

  @NotNull(message = "A Comment must have a title")
  private String title;

  @NotNull(message = "A Comment must have an associated description")
  private String description;

  @Column(name = "TASK_ID")
  private long taskId;

  public CommentEntity() {}
}
