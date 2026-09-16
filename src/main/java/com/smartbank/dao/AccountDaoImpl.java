package com.smartbank.dao;

import com.smartbank.entity.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Account save(Account account) {
        session().saveOrUpdate(account);
        return account;
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        Query<Account> query = session().createQuery(
                "FROM Account WHERE accountNumber = :accNo", Account.class);
        query.setParameter("accNo", accountNumber);
        return query.uniqueResultOptional();
    }

    @Override
    public Optional<Account> findByUpiId(String upiId) {
        Query<Account> query = session().createQuery(
                "FROM Account a JOIN FETCH a.user WHERE a.upiId = :upiId", Account.class);
        query.setParameter("upiId", upiId);
        return query.uniqueResultOptional();
    }

    @Override
    public Optional<Account> findByUserId(Long userId) {
        Query<Account> query = session().createQuery(
                "FROM Account WHERE user.id = :userId", Account.class);
        query.setParameter("userId", userId);
        return query.uniqueResultOptional();
    }
}
