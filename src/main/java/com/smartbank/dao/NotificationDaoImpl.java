package com.smartbank.dao;
import com.smartbank.entity.Notification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class NotificationDaoImpl implements NotificationDao {
    @Autowired private SessionFactory sessionFactory;
    private Session session(){return sessionFactory.getCurrentSession();}
    public Notification save(Notification n){session().saveOrUpdate(n);return n;}
    public List<Notification> findLatestByUserId(Long userId,int n){
        return session().createQuery("FROM Notification WHERE user.id=:uid ORDER BY createdAt DESC",Notification.class)
                .setParameter("uid",userId).setMaxResults(n).list();
    }
    public long countUnread(Long userId){
        return session().createQuery("SELECT COUNT(n) FROM Notification n WHERE n.user.id=:uid AND n.readFlag=false",Long.class)
                .setParameter("uid",userId).uniqueResult();
    }
    public void markAllRead(Long userId){
        session().createQuery("UPDATE Notification SET readFlag=true WHERE user.id=:uid")
                .setParameter("uid",userId).executeUpdate();
    }
}
