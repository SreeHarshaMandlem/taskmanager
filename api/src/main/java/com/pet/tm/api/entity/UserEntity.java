package com.pet.tm.api.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "USER")
@Data
@Builder
public class UserEntity {

  @Id @GeneratedValue private Long id;

  @NotNull(message = "A User must have a name")
  private String name;
}
