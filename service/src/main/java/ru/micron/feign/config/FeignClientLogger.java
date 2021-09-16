package ru.micron.feign.config;

import feign.Request;
import feign.Response;
import feign.slf4j.Slf4jLogger;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientLogger extends Slf4jLogger {

  @Override
  protected void logRequest(String configKey, Level logLevel, Request request) {
    if (!log.isInfoEnabled()) {
      return;
    }

    StringBuilder loggerOutput = new StringBuilder("[" + configKey + "] -> ");
    loggerOutput.append(request.httpMethod()).append(" ")
        .append(request.url())
        .append('\n');

    String body = getBody(request);
    if (!body.isBlank()) {
      loggerOutput.append("Body: ");
      loggerOutput.append(body);
    }
    log.info(loggerOutput.toString());
  }

  @Override
  protected Response logAndRebufferResponse(
      String configKey, Level logLevel, Response response, long elapsedTime
  ) {
    return response;
  }

  private String getBody(Request request) {
    return Objects.nonNull(request.body()) && Objects.nonNull(request.charset())
        ? new String(request.body(), request.charset())
        : "";
  }
}
