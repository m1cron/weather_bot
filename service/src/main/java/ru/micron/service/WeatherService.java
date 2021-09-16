package ru.micron.service;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.micron.feign.OpenWeatherMapFeignClient;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WeatherService {

  private final OpenWeatherMapFeignClient openWeatherMapFeignClient;

  @PostConstruct
  @GetMapping("/")
  public void test() {
    var test = openWeatherMapFeignClient
        .getWeather("moscow", "", "ru");

    log.error(test.toString());
  }
}
