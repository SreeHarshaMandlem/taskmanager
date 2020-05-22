package com.pet.tm.api.controller;

import com.pet.tm.api.entity.TaskEntity;
import com.pet.tm.api.service.TaskService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/task")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public TaskEntity createTask(@NonNull @RequestBody TaskEntity task) {
    return taskService.createTask(task);
  }
}
