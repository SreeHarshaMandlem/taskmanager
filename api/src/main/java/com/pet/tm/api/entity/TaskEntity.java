package com.pet.tm.api.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "TASK")
@Data
public class TaskEntity {

  @Id @GeneratedValue private Long id;

  private String title;

  private String description;

  private String status;
}
