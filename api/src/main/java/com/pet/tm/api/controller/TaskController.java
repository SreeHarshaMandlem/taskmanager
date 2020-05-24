package com.pet.tm.api.controller;

import com.pet.tm.api.entity.CommentEntity;
import com.pet.tm.api.entity.TaskEntity;
import com.pet.tm.api.service.TaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping("/api/task")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public TaskEntity createTask(@Valid @NotNull @RequestBody TaskEntity task) {
    return taskService.createTask(task);
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public List<TaskEntity> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
  public TaskEntity getTask(@NotNull @PathVariable("id") Long id) {
    return taskService.getTaskById(id);
  }

  @PostMapping(
      path = "/{taskId}/comment",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  public void addComment(
      @NotNull @PathVariable("taskId") Long taskId,
      @Valid @NotNull @RequestBody CommentEntity comment) {
    taskService.addComment(taskId, comment);
  }

  @PutMapping(
      path = "/{taskId}/unassign")
  public void unassignTask(
      @NotNull @PathVariable("taskId") Long taskId) {
    taskService.unassign(taskId);
  }
}
