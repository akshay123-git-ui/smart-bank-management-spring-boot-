package com.smartbank.dao;

import com.smartbank.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    // getCurrentSession() (not openSession()) ties the Session to whatever
    // Spring-managed transaction is active - that's what @Transactional on
    // the Service layer is coordinating for us.
    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public User save(User user) {
        session().saveOrUpdate(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(session().get(User.class, id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Query<User> query = session().createQuery(
                "FROM User WHERE email = :email", User.class);
        query.setParameter("email", email);
        return query.uniqueResultOptional();
    }

    @Override
    public List<User> findAllCustomers() {
        Query<User> query = session().createQuery(
                "FROM User WHERE role = :role ORDER BY createdAt DESC", User.class);
        query.setParameter("role", "CUSTOMER");
        return query.list();
    }

    @Override
    public boolean existsByEmail(String email) {
        Query<Long> query = session().createQuery(
                "SELECT COUNT(id) FROM User WHERE email = :email", Long.class);
        query.setParameter("email", email);
        return query.uniqueResult() > 0;
    }
}
