package com.pet.tm.api.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "TASK")
@Data
public class TaskEntity {

  @Id @GeneratedValue private Long id;

  @NotNull(message = "A Task must have a title")
  private String title;

  @NotNull(message = "A Task must have an associated description")
  private String description;

  private String status = "open";
}
