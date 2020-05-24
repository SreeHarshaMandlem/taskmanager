package com.pet.tm.api.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "TASK")
@Data
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
}
