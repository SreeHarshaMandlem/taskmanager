package com.pet.tm.api.service;

import com.pet.tm.api.entity.TaskEntity;
import com.pet.tm.api.exception.InvalidPropertyValueException;
import com.pet.tm.api.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private static Logger LOG = LoggerFactory.getLogger(TaskService.class);

  private final TaskRepository taskRepository;

  public TaskService(final TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public TaskEntity createTask(@NonNull TaskEntity task) {
    validteTask(task);
    LOG.info("Creating Task: \n \t\t{}", task);
    return taskRepository.save(task);
  }

  private void validteTask(TaskEntity task) {
    if (task.getAssignee() != null && task.getAssignee().getId() == null) {
      throw new InvalidPropertyValueException("Assignee's id cannot be null.");
    }
  }
}
