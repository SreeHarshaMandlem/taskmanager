package com.pet.tm.api;

import com.pet.tm.api.dto.ErrorDto;
import com.pet.tm.api.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
