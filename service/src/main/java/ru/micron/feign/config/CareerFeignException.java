package ru.micron.feign.config;

import feign.FeignException;
import feign.Request;

public class CareerFeignException extends FeignException {

  protected CareerFeignException(int status, String message) {
    super(status, message);
  }

  protected CareerFeignException(int status, String message, Throwable cause) {
    super(status, message, cause);
  }

  protected CareerFeignException(int status, String message, Request request, Throwable cause) {
    super(status, message, request, cause);
  }
}

