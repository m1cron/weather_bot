package ru.micron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "ru.micron")
@SpringBootApplication(scanBasePackages = "ru.micron")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
