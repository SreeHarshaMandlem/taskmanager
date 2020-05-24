package com.pet.tm.api.controller;

import com.pet.tm.api.dto.ErrorDto;
import com.pet.tm.api.exception.InvalidPropertyValueException;
import com.pet.tm.api.exception.TaskNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handles {@link TaskNotFoundException} types.
   *
   * @param exception of type TaskNotFoundException
   * @return ErrorDto
   */
  @ExceptionHandler(TaskNotFoundException.class)
  public ErrorDto handleTaskNotFoundException(TaskNotFoundException exception) {
    return ErrorDto.builder()
        .code(HttpStatus.NOT_FOUND.value())
        .message(exception.getMessage())
        .build();
  }

  /**
   * Handles {@link InvalidPropertyValueException} types.
   *
   * @param exception of type InvalidPropertyValueException
   * @return ErrorDto
   */
  @ExceptionHandler(InvalidPropertyValueException.class)
  public ErrorDto handleInvalidPropertyValueException(InvalidPropertyValueException exception) {
    return ErrorDto.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(exception.getMessage())
        .build();
  }

  /**
   * Handles {@link HttpRequestMethodNotSupportedException} types.
   *
   * @param exception of type HttpRequestMethodNotSupportedException
   * @return ErrorDto
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ErrorDto handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException exception) {
    return ErrorDto.builder()
        .code(HttpStatus.METHOD_NOT_ALLOWED.value())
        .message(exception.getMessage())
        .build();
  }

  /**
   * Handles {@link MethodArgumentNotValidException} types.
   *
   * @param exception of type MethodArgumentNotValidException
   * @return ErrorDto
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    return ErrorDto.builder()
        .code(HttpStatus.PRECONDITION_FAILED.value())
        .message(exception.getMessage())
        .build();
  }
}
