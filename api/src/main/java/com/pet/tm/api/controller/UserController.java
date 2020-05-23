package com.pet.tm.api.controller;

import com.pet.tm.api.entity.UserEntity;
import com.pet.tm.api.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public UserEntity createUser(@Valid @NotNull @RequestBody UserEntity user) {
    return userService.createUser(user);
  }
}
