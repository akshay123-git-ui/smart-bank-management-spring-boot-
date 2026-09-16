package com.smartbank.dao;
import com.smartbank.entity.Notification;
import java.util.List;
public interface NotificationDao {
    Notification save(Notification n);
    List<Notification> findLatestByUserId(Long userId,int n);
    long countUnread(Long userId);
    void markAllRead(Long userId);
}
