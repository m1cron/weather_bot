package ru.micron.feign.config;

import feign.FeignException;
import feign.Request;

public class CustomFeignException extends FeignException {

  protected CustomFeignException(int status, String message) {
    super(status, message);
  }

  protected CustomFeignException(int status, String message, Throwable cause) {
    super(status, message, cause);
  }

  protected CustomFeignException(int status, String message, Request request, Throwable cause) {
    super(status, message, request, cause);
  }
}

