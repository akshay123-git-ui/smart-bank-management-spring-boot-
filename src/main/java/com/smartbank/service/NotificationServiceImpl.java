package com.smartbank.service;
import com.smartbank.dao.NotificationDao;
import com.smartbank.dao.UserDao;
import com.smartbank.entity.Notification;
import com.smartbank.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired private NotificationDao notificationDao;
    @Autowired private UserDao userDao;
    @Transactional public void notify(Long userId,String message,String type){
        User u=userDao.findById(userId).orElse(null);
        if(u!=null) notificationDao.save(new Notification(u,message,type));
    }
    @Transactional(readOnly=true) public List<Notification> latest(Long userId){return notificationDao.findLatestByUserId(userId,20);}
    @Transactional(readOnly=true) public long unreadCount(Long userId){return notificationDao.countUnread(userId);}
    @Transactional public void markAllRead(Long userId){notificationDao.markAllRead(userId);}
}
