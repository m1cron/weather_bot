package ru.micron.feign.config;

import static feign.Util.UTF_8;
import static feign.Util.decodeOrDefault;

import feign.Request;
import feign.Response;
import feign.Util;
import feign.slf4j.Slf4jLogger;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientLogger extends Slf4jLogger {

  private static final int MAX_BODY_SIZE = 2000;
  private static final String BINARY_DARA = "Binary data";

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
  protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response,
      long elapsedTime) throws IOException {
    if (!log.isInfoEnabled()) {
      return response;
    }

    StringBuilder loggerOutput = new StringBuilder("[" + configKey + "] <- ");
    int status = response.status();
    loggerOutput.append("HTTP/1.1 ")
        .append(status)
        .append(" (").append(elapsedTime).append("ms)")
        .append('\n');

    if (log.isTraceEnabled()) {

      if (Objects.nonNull(response.body()) && bodyIsExpected(status)) {
        loggerOutput.append("\nBody: ");
        byte[] fullBodyData = Util.toByteArray(response.body().asInputStream());
        byte[] trimmedBodyData = fullBodyData.length < MAX_BODY_SIZE ?
            fullBodyData :
            Arrays.copyOf(fullBodyData, MAX_BODY_SIZE);
        if (trimmedBodyData.length > 0) {
          log(configKey, "%s", decodeOrDefault(trimmedBodyData, UTF_8, BINARY_DARA));
          loggerOutput.append(decodeOrDefault(trimmedBodyData, UTF_8, BINARY_DARA));
        }
        log.info(loggerOutput.toString());
        return response.toBuilder().body(fullBodyData).build();
      } else {
        log.info(loggerOutput.toString());
      }
    }
    return response;
  }

  private boolean bodyIsExpected(int status) {
    // HTTP 204 No Content "...response MUST NOT include a message-body"
    // HTTP 205 Reset Content "...response MUST NOT include an entity"

    return !(status == 204 || status == 205);
  }

  private String getBody(Request request) {
    return Objects.nonNull(request.body()) && Objects.nonNull(request.charset())
        ? new String(request.body(), request.charset())
        : "";
  }
}
