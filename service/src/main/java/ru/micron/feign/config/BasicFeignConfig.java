package ru.micron.feign.config;

import feign.Logger;
import feign.Logger.Level;
import feign.Request;
import feign.codec.ErrorDecoder;
import java.util.concurrent.TimeUnit;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.DefaultFeignLoggerFactory;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;

@NoArgsConstructor
public class BasicFeignConfig {

  @Bean
  public FeignLoggerFactory feignLoggerFactory() {
    return new DefaultFeignLoggerFactory(new FeignClientLogger());
  }

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Level.FULL;
  }

  @Bean
  public static Request.Options requestOptions() {
    return new Request.Options(
        30000,
        TimeUnit.MILLISECONDS,
        60000,
        TimeUnit.MILLISECONDS,
        true
    );
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return new FeignErrorDecoder();
  }
}

