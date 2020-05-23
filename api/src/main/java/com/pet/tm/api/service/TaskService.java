package com.pet.tm.api.service;

import com.pet.tm.api.entity.TaskEntity;
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
    LOG.info("Creating Task: \n \t\t{}", task);
    return taskRepository.save(task);
  }
}
