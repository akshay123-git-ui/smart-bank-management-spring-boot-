package com.smartbank.dao;
import com.smartbank.entity.Card;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public class CardDaoImpl implements CardDao {
    @Autowired private SessionFactory sessionFactory;
    private Session session(){return sessionFactory.getCurrentSession();}
    public Card save(Card c){session().saveOrUpdate(c);return c;}
    public Optional<Card> findByUserId(Long userId){
        return session().createQuery("FROM Card WHERE user.id=:uid",Card.class)
                .setParameter("uid",userId).uniqueResultOptional();
    }
}
