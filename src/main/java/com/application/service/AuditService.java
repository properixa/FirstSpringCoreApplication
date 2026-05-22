package com.application.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AuditService {
    private final static String AUDIT_FILE = "audit.log";
    private final NotificationService notificationService;

    public AuditService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void log(String message) {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logLine = timestamp + " - " + message;

        try (PrintWriter writer = new PrintWriter(new FileWriter(AUDIT_FILE, true))) {
            writer.println(logLine);
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    public void logWithNotify(String message) {
        log(message);
        notificationService.sendNotify(message);
    }
}
