package com.pet.tm.api;

import com.pet.tm.api.dto.ErrorDto;
import com.pet.tm.api.entity.CommentEntity;
import com.pet.tm.api.entity.TaskEntity;
import com.pet.tm.api.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    assertEquals(HttpStatus.PRECONDITION_FAILED.value(), response.getBody().getCode());

    ErrorDto errorDto = response.getBody();

    Assertions.assertNotNull(errorDto);
    Assertions.assertTrue(errorDto.getMessage().contains("title"));
  }

  @Test
  public void whenCreatingTask_IfDescIsEmpty_Expect_ErrorDto_MethodArgumentNotValidException() {
    ResponseEntity<ErrorDto> response =
        testRestTemplate.postForEntity(
            TASK_API, TaskEntity.builder().title("Test title").build(), ErrorDto.class);

    assertEquals(HttpStatus.PRECONDITION_FAILED.value(), response.getBody().getCode());

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

    assertEquals(HttpStatus.OK, response.getStatusCode());

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

    assertEquals(HttpStatus.OK, response.getStatusCode());

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

    assertEquals(HttpStatus.OK, response.getStatusCode());

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
    // Precondition Tasks must exist
    for (int i = 0; i < 2; i++) {
      ResponseEntity<TaskEntity> taskResponse =
          testRestTemplate.postForEntity(
              TASK_API,
              TaskEntity.builder().title("Test title").description("Test description").build(),
              TaskEntity.class);
    }

    // Test
    ResponseEntity<TaskEntity[]> response =
        testRestTemplate.getForEntity(TASK_API, TaskEntity[].class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    TaskEntity[] tasks = response.getBody();

    assertNotNull(tasks, "Expected: Not null");
    assertTrue(tasks.length > 0);
  }

  @Test
  public void whenAddingComment_IfTitleIsEmpty_Expect_ErrorDto_MethodArgumentNotValidException() {
    // Precondition Task must exist
    ResponseEntity<TaskEntity> preResponse =
        testRestTemplate.postForEntity(
            TASK_API,
            TaskEntity.builder().title("Test title").description("Test description").build(),
            TaskEntity.class);

    // Test
    ResponseEntity<ErrorDto> response =
        testRestTemplate.postForEntity(
            TASK_API + "/" + preResponse.getBody().getId() + "/comment",
            CommentEntity.builder().description("Test_Description").build(),
            ErrorDto.class);

    assertEquals(HttpStatus.PRECONDITION_FAILED.value(), response.getBody().getCode());

    ErrorDto errorDto = response.getBody();

    Assertions.assertNotNull(errorDto);
    Assertions.assertTrue(errorDto.getMessage().contains("title"));
  }

  @Test
  public void whenAddingComment_IfDescIsEmpty_Expect_ErrorDto_MethodArgumentNotValidException() {
    // Precondition Task must exist
    ResponseEntity<TaskEntity> preResponse =
        testRestTemplate.postForEntity(
            TASK_API,
            TaskEntity.builder().title("Test title").description("Test description").build(),
            TaskEntity.class);

    // Test
    ResponseEntity<ErrorDto> response =
        testRestTemplate.postForEntity(
            TASK_API + "/" + preResponse.getBody().getId() + "/comment",
            CommentEntity.builder().title("Test_Title").build(),
            ErrorDto.class);

    assertEquals(HttpStatus.PRECONDITION_FAILED.value(), response.getBody().getCode());

    ErrorDto errorDto = response.getBody();

    Assertions.assertNotNull(errorDto);
    Assertions.assertTrue(errorDto.getMessage().contains("description"));
  }

  @Test
  public void whenAddingComment_IfTaskDoesNotExist_Expect_ErrorDto_TaskNotFoundException() {
    // Test
    ResponseEntity<ErrorDto> response =
        testRestTemplate.postForEntity(
            TASK_API + "/1001" + "/comment",
            CommentEntity.builder().title("Test Title").description("Test_Description").build(),
            ErrorDto.class);

    ErrorDto errorDto = response.getBody();

    Assertions.assertNotNull(errorDto);
    Assertions.assertTrue(errorDto.getMessage().contains("Task with id: 1001 does not exist"));
  }

  @Test
  public void shouldAddNewComment() {
    // Precondition Task must exist
    ResponseEntity<TaskEntity> preResponse =
        testRestTemplate.postForEntity(
            TASK_API,
            TaskEntity.builder().title("Test title").description("Test description").build(),
            TaskEntity.class);

    assertNull(preResponse.getBody().getComments());
    // Test
    ResponseEntity response =
        testRestTemplate.postForEntity(
            TASK_API + "/" + preResponse.getBody().getId() + "/comment",
            CommentEntity.builder().title("Test Title").description("Test_Description").build(),
            null);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    ResponseEntity<TaskEntity> postResponse =
        testRestTemplate.getForEntity(
            TASK_API + "/" + preResponse.getBody().getId(), TaskEntity.class);

    assertEquals(1, postResponse.getBody().getComments().size());
  }
}
