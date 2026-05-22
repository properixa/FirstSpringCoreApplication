package com.application.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final AuditService auditService;

    public NotificationService(@Lazy AuditService auditService) {
        this.auditService = auditService;
    }

    public void sendNotify(String message) {
        System.out.println("Notification: " + message);
    }

    public void sendNotifyWithLog(String message) {
        sendNotify(message);
        auditService.log(message);
    }
}
