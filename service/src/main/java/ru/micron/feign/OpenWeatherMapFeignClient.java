package ru.micron.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.micron.dto.OpenWeatherMapDto;
import ru.micron.feign.config.BasicFeignConfig;

@FeignClient(
    name = "openWeatherMapFeignClient",
    url = "http://api.openweathermap.org/data/2.5",
    configuration = BasicFeignConfig.class
)
public interface OpenWeatherMapFeignClient {

  @GetMapping("/weather")
  OpenWeatherMapDto getWeather(
      @RequestParam("q") String cityName,
      @RequestParam("appid") String apiKey,
      @RequestParam("lang") String lang
  );
}
