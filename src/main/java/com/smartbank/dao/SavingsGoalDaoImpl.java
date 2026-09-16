package com.smartbank.dao;
import com.smartbank.entity.SavingsGoal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public class SavingsGoalDaoImpl implements SavingsGoalDao {
    @Autowired private SessionFactory sessionFactory;
    private Session session(){return sessionFactory.getCurrentSession();}
    public SavingsGoal save(SavingsGoal g){session().saveOrUpdate(g);return g;}
    public List<SavingsGoal> findByUserId(Long userId){
        return session().createQuery("FROM SavingsGoal WHERE user.id=:uid ORDER BY targetDate",SavingsGoal.class)
                .setParameter("uid",userId).list();
    }
    public Optional<SavingsGoal> findByIdAndUserId(Long id,Long userId){
        return session().createQuery("FROM SavingsGoal WHERE id=:id AND user.id=:uid",SavingsGoal.class)
                .setParameter("id",id).setParameter("uid",userId).uniqueResultOptional();
    }
}
