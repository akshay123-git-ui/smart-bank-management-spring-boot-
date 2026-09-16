package com.smartbank.dao;

import com.smartbank.entity.Loan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LoanDaoImpl implements LoanDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Loan save(Loan loan) {
        session().saveOrUpdate(loan);
        return loan;
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return Optional.ofNullable(session().get(Loan.class, id));
    }

    @Override
    public List<Loan> findByUserId(Long userId) {
        Query<Loan> query = session().createQuery(
                "FROM Loan WHERE user.id = :userId ORDER BY appliedDate DESC", Loan.class);
        query.setParameter("userId", userId);
        return query.list();
    }

    @Override
    public List<Loan> findAll() {
        return session().createQuery(
                "FROM Loan ORDER BY appliedDate DESC", Loan.class).list();
    }

    @Override
    public List<Loan> findByStatus(String status) {
        Query<Loan> query = session().createQuery(
                "FROM Loan WHERE status = :status ORDER BY appliedDate ASC", Loan.class);
        query.setParameter("status", status);
        return query.list();
    }
}
