package com.smartbank.dao;

import com.smartbank.entity.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Transaction save(Transaction transaction) {
        session().saveOrUpdate(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findLastNByAccountId(Long accountId, int n) {

        Query<Transaction> q = session().createQuery(
                "SELECT t FROM Transaction t " +
                "JOIN FETCH t.account " +
                "WHERE t.account.id = :accountId " +
                "ORDER BY t.timestamp DESC",
                Transaction.class
        );

        return q.setParameter("accountId", accountId)
                .setMaxResults(n)
                .list();
    }

    @Override
    public List<Transaction> findAllRecent(int n) {

        Query<Transaction> q = session().createQuery(
                "SELECT t FROM Transaction t " +
                "JOIN FETCH t.account " +
                "ORDER BY t.timestamp DESC",
                Transaction.class
        );

        return q.setMaxResults(n).list();
    }
}