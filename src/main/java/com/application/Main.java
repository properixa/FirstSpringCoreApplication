package com.application;


import com.application.config.AppConfig;
import com.application.console.ConsoleInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ConsoleInterface console = context.getBean(ConsoleInterface.class);

        console.run();
    }
}
