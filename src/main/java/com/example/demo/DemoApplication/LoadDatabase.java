package com.example.demo.DemoApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// When class is loaded
// Spring boot will run all CommandLineRunner when application context is loaded
// runner will reuqest a copy of CustomerRepository
@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger((LoadDatabase.class));

    @Bean // These beans will be run every time application context is loaded
        // This one request the CustomerRepository interface to interact with database
    CommandLineRunner initDatabase(CustomerRepository CustomerRepository){
        return args -> {};
    }


}
