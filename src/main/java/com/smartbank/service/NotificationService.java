package com.smartbank.service;
import com.smartbank.entity.Notification;
import java.util.List;
public interface NotificationService {
    void notify(Long userId,String message,String type);
    List<Notification> latest(Long userId);
    long unreadCount(Long userId);
    void markAllRead(Long userId);
}
