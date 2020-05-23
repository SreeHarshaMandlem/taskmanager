package com.pet.tm.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ErrorDto {

  private String id;

  private String message;

  private int code;

  /**
   * This builder is used by Lombok with @Builder annotation. While @Builder normally generates a
   * similar builder, to auto generate id this custom builder is added.
   */
  public static class ErrorDtoBuilder {
    private String id;

    private String message;

    private int code;

    public ErrorDtoBuilder message(String message) {
      this.message = message;
      return this;
    }

    public ErrorDtoBuilder code(int code) {
      this.code = code;
      return this;
    }

    public ErrorDto build() {
      return new ErrorDto(UUID.randomUUID().toString(), message, code);
    }
  }
}
