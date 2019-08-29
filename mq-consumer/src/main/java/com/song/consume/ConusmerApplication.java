package com.song.consume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ConusmerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConusmerApplication.class, args);
    }

}
