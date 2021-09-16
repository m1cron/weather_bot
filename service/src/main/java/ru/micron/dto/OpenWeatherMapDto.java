package ru.micron.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapDto {

  private List<Weather> weather;
  private Main main;
  private Wind wind;
  private Integer cod;

  @Data
  @NoArgsConstructor
  @Accessors(chain = true)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Weather {
    private String description;
  }

  @Data
  @NoArgsConstructor
  @Accessors(chain = true)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Main {
    private Integer temp;
    private Integer humidity;
  }

  @Data
  @NoArgsConstructor
  @Accessors(chain = true)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Wind {
    private Integer speed;
  }
}
