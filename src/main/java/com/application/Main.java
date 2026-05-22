package com.application;


import com.application.config.AppConfig;
import com.application.console.ConsoleInterface;
import com.application.service.AuditService;
import com.application.service.NotificationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ConsoleInterface console = context.getBean(ConsoleInterface.class);
        AuditService auditService = context.getBean(AuditService.class);
        auditService.logWithNotify("Message1");
        auditService.log("hello1");

        NotificationService notificationService = context.getBean(NotificationService.class);
        notificationService.sendNotify("My first notify");
        notificationService.sendNotifyWithLog("My first log with notify");

        console.run();
    }
}