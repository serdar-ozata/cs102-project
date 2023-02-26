package com.example.application;

import database.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.vaadin.artur.helpers.LaunchUtil;

import java.util.ArrayList;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {


    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        dataSource.open();
        dataSource.createDB();

        double[] doubles = new double[2];
        doubles[0] = 1;
        doubles[1] = 0;
        dataSource.addPerson("1", "Serdar", "1", 0, "ornek", 0,0);
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args));
    }

}
