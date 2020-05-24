package com.pet.tm.api;

import com.pet.tm.api.dto.ErrorDto;
import com.pet.tm.api.entity.TaskEntity;
import com.pet.tm.api.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

  private static final String TASK_API = "/api/task";

  private static final String USER_API = "/api/user";

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
  public void shouldCreateTask_WithOutAssignee() {
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

  @Test
  public void shouldCreateTask_WithAssignee() {
    // Precondition User must exist
    ResponseEntity<UserEntity> responseUser =
        testRestTemplate.postForEntity(
            USER_API, UserEntity.builder().name("Test Name").build(), UserEntity.class);

    UserEntity userEntity = responseUser.getBody();

    ResponseEntity<TaskEntity> response =
        testRestTemplate.postForEntity(
            TASK_API,
            TaskEntity.builder()
                .title("Test title")
                .description("Test description")
                .assignee(userEntity)
                .build(),
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

    assertNotNull(taskEntity.getAssignee());
    assertEquals(userEntity.getId(), taskEntity.getAssignee().getId());
  }

  @Test
  public void shouldGetTask() {
    // Precondition Task must exist
    ResponseEntity<TaskEntity> taskResponse =
        testRestTemplate.postForEntity(
            TASK_API,
            TaskEntity.builder().title("Test title").description("Test description").build(),
            TaskEntity.class);

    ResponseEntity<TaskEntity> response =
        testRestTemplate.getForEntity(
            TASK_API + "/" + taskResponse.getBody().getId(), TaskEntity.class);

    TaskEntity taskEntity = response.getBody();

    assertNotNull(taskEntity, "Task entity is null. Expected: Not null");
    assertNotNull(taskEntity.getId(), "Task entity {id} is null. Expected: Not null");
    assertEquals(
        taskResponse.getBody().getId(),
        taskEntity.getId(),
        "Task entity id is not as expected: " + taskResponse.getBody().getId());
    assertEquals(
        taskResponse.getBody().getTitle(),
        taskEntity.getTitle(),
        "Task entity title is not as expected: " + taskResponse.getBody().getTitle());
    assertEquals(
        taskResponse.getBody().getDescription(),
        taskEntity.getDescription(),
        "Task entity title is not as expected: " + taskResponse.getBody().getDescription());

    assertNull(taskEntity.getAssignee());
  }

  @Test
  public void shouldGetTasks() {
    // Precondition Task must exist
    int numberOfTasks = 2;
    for (int i = 0; i < numberOfTasks; i++) {
      ResponseEntity<TaskEntity> taskResponse =
          testRestTemplate.postForEntity(
              TASK_API,
              TaskEntity.builder().title("Test title").description("Test description").build(),
              TaskEntity.class);
    }

    ResponseEntity<List> response = testRestTemplate.getForEntity(TASK_API, List.class);

    List tasks = response.getBody();

    assertNotNull(tasks, "Expected: Not null");
    assertEquals(numberOfTasks, tasks.size());
  }
}
