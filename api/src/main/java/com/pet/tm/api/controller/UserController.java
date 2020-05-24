package com.pet.tm.api.controller;

import com.pet.tm.api.entity.TaskEntity;
import com.pet.tm.api.entity.UserEntity;
import com.pet.tm.api.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

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

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public List<UserEntity> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
  public UserEntity getUser(@NotNull @PathVariable("id") Long id) {
    return userService.getUserById(id);
  }
}
