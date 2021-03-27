package com.parser.power;

import com.parser.power.models.Converter;
import com.parser.power.repositories.ConverterRepository;
import com.parser.power.repositories.ConverterRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PowerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerApplication.class, args);
    }

}
