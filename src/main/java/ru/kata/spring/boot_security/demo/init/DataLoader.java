package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {
    private final DataInitializationService dataInitializationService;

    public DataLoader( DataInitializationService dataInitializationService) {
        this.dataInitializationService = dataInitializationService;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            dataInitializationService.createTablesAndData();
        };
    }
}
