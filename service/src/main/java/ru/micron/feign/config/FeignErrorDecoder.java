package ru.micron.feign.config;

import feign.Request;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@NoArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

  private static final String FEIGN_ERROR_MESSAGE_PATTERN =
      "из сервиса %s с URL %s на вызов метода %s \n"
          + "с запросом: \n%s \nв ответе получен HTTP статус: %d %s \n"
          + "и тело ответа: \n%s \n";

  @Override
  public Exception decode(String methodKey, Response response) {
    var message = String.format(FEIGN_ERROR_MESSAGE_PATTERN,
        getServiceName(response.request()),
        response.request().url(),
        methodKey,
        response.request(),
        response.status(),
        HttpStatus.valueOf(response.status()),
        response.body()
    );
    return new CareerFeignException(response.status(), message, response.request(), null);
  }

  private String getServiceName(Request request) {
    return request.url().replaceAll("^(.*:)//([A-Za-z0-9\\-.]+)(:[0-9]+)?(.*)$", "$2");
  }
}
