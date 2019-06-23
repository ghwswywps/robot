package com.robot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
}
