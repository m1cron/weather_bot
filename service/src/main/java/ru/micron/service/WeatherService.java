package ru.micron.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.micron.dto.OpenWeatherMapDto;
import ru.micron.feign.OpenWeatherMapFeignClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

  private static final short ABSOLUTE_TEMPERATURE_OFFSET = 273;
  private static final String WEATHER_MESSAGE_TEMPLATE = "В городе %s\n" +
      "погода: %s\n" +
      "температура: %d градусов\n" +
      "влажность: %d%%\n" +
      "скорость ветра: %d м/с\n";

  private final OpenWeatherMapFeignClient openWeatherMapFeignClient;

  @Value("${app.feign.openWeatherMap.apiKey}")
  private String apiKey;

  @SneakyThrows
  public String getWeather(String city) {
    OpenWeatherMapDto weather = openWeatherMapFeignClient
        .getWeatherByCity(apiKey, city, "ru");
    if (weather.getCod() != HttpStatus.OK.value()) {
      throw new Exception("HttpStatus is not 200");
    }
    return String.format(
        WEATHER_MESSAGE_TEMPLATE,
        city,
        weather.getWeather().get(0).getDescription(),
        weather.getMain().getTemp() - ABSOLUTE_TEMPERATURE_OFFSET,
        weather.getMain().getHumidity(),
        weather.getWind().getSpeed());
  }
}
