package com.pet.tm.api;

import com.pet.tm.api.dto.ErrorDto;
import com.pet.tm.api.entity.TaskEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

  private static final String TASK_API = "/api/task";

  @Autowired private TestRestTemplate testRestTemplate = new TestRestTemplate();

  @Test
  public void whenCreatingTask_IfTitleIsEmpty_Expect_ErrorDto_MethodArgumentNotValidException() {
    ResponseEntity<ErrorDto> response =
        testRestTemplate.postForEntity(
            TASK_API, TaskEntity.builder().description("Test description").build(), ErrorDto.class);

    ErrorDto errorDto = response.getBody();

    Assertions.assertNotNull(errorDto);
    Assertions.assertTrue(errorDto.getMessage().contains("title"));
  }

  @Test
  public void whenCreatingTask_IfDescIsEmpty_Expect_ErrorDto_MethodArgumentNotValidException() {
    ResponseEntity<ErrorDto> response =
        testRestTemplate.postForEntity(
            TASK_API, TaskEntity.builder().title("Test title").build(), ErrorDto.class);

    ErrorDto errorDto = response.getBody();

    Assertions.assertNotNull(errorDto);
    Assertions.assertTrue(errorDto.getMessage().contains("description"));
  }

  @Test
  public void shouldCreateTask() {
    ResponseEntity<TaskEntity> response =
        testRestTemplate.postForEntity(
            TASK_API,
            TaskEntity.builder().title("Test title").description("Test description").build(),
            TaskEntity.class);

    TaskEntity taskEntity = response.getBody();

    assertNotNull(taskEntity, "Task entity is null. Expected: Not null");
    assertNotNull(taskEntity.getId(), "Task entity {id} is null. Expected: Not null");
    assertEquals(
        "Test title", taskEntity.getTitle(), "Task entity title is not as expected: Test title");
    assertEquals(
        "Test description",
        taskEntity.getDescription(),
        "Task entity title is not as expected: Test description");
  }
}
