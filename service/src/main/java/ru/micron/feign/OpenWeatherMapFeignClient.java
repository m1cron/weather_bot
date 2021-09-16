package ru.micron.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.micron.dto.OpenWeatherMapDto;
import ru.micron.feign.config.BasicFeignConfig;

@FeignClient(
    name = "openWeatherMapFeignClient",
    url = "${app.feign.openWeatherMap.url}",
    configuration = BasicFeignConfig.class)
public interface OpenWeatherMapFeignClient {

  @GetMapping("/weather")
  OpenWeatherMapDto getWeatherByCity(
      @RequestParam("appid") String apiKey,
      @RequestParam("q") String cityName,
      @RequestParam("lang") String lang
  );
}
