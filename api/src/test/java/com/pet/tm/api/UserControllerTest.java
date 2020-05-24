package com.pet.tm.api;

import com.pet.tm.api.dto.ErrorDto;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

  private static final String USER_API = "/api/user";

  @Autowired private TestRestTemplate testRestTemplate = new TestRestTemplate();

  @Test
  public void whenCreatingUser_IfNameIsEmpty_Expect_ErrorDto_MethodArgumentNotValidException() {
    ResponseEntity<ErrorDto> response =
        testRestTemplate.postForEntity(USER_API, UserEntity.builder().build(), ErrorDto.class);

    ErrorDto errorDto = response.getBody();

    Assertions.assertNotNull(errorDto);
    Assertions.assertTrue(errorDto.getMessage().contains("name"));
  }

  @Test
  public void shouldCreateUser() {
    ResponseEntity<UserEntity> response =
        testRestTemplate.postForEntity(
            USER_API, UserEntity.builder().name("Test name").build(), UserEntity.class);

    UserEntity userEntity = response.getBody();

    assertNotNull(userEntity, "User entity is null. Expected: Not null");
    assertNotNull(userEntity.getId(), "User entity {id} is null. Expected: Not null");
    assertEquals(
        "Test name", userEntity.getName(), "User entity name is not as expected is: Test name");
  }

  @Test
  public void shouldGetUser() {
    // Precondition User must exist
    ResponseEntity<UserEntity> userResponse =
        testRestTemplate.postForEntity(
            USER_API, UserEntity.builder().name("Test name").build(), UserEntity.class);

    ResponseEntity<UserEntity> response =
        testRestTemplate.getForEntity(
            USER_API + "/" + userResponse.getBody().getId(), UserEntity.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    UserEntity userE = response.getBody();

    assertNotNull(userE, "User entity is null. Expected: Not null");
    assertNotNull(userE.getId(), "User entity {id} is null. Expected: Not null");
    assertEquals(
        userResponse.getBody().getId(),
        userE.getId(),
        "User entity id is not as expected: " + userResponse.getBody().getId());
    assertEquals(
        userResponse.getBody().getName(),
        userE.getName(),
        "User entity title is not as expected: " + userResponse.getBody().getName());
  }

  @Test
  public void shouldGetTasks() {
    // Precondition Tasks must exist
    for (int i = 0; i < 2; i++) {
      ResponseEntity<UserEntity> userResponse =
          testRestTemplate.postForEntity(
              USER_API, UserEntity.builder().name("Test name").build(), UserEntity.class);
    }

    // Test
    ResponseEntity<UserEntity[]> response =
        testRestTemplate.getForEntity(USER_API, UserEntity[].class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    UserEntity[] users = response.getBody();

    assertNotNull(users, "Expected: Not null");
    assertTrue(users.length > 0);
  }

  @Test
  public void whenFetchingUser_IfUserDoesNotExist_Expect_ErrorDto_UserNotFoundException() {
    ResponseEntity<ErrorDto> response =
        testRestTemplate.getForEntity(USER_API + "/" + "1001", ErrorDto.class);

    assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getCode());

    ErrorDto errorDto = response.getBody();

    Assertions.assertNotNull(errorDto);
    Assertions.assertTrue(errorDto.getMessage().contains("User with id: 1001 does not exist"));
  }
}
