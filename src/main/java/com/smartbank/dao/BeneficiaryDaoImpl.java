package com.smartbank.dao;
import com.smartbank.entity.Beneficiary;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public class BeneficiaryDaoImpl implements BeneficiaryDao {
    @Autowired private SessionFactory sessionFactory;
    private Session session(){return sessionFactory.getCurrentSession();}
    public Beneficiary save(Beneficiary b){session().saveOrUpdate(b);return b;}
    public List<Beneficiary> findByOwnerId(Long userId){
        return session().createQuery("FROM Beneficiary WHERE owner.id=:uid ORDER BY name",Beneficiary.class)
                .setParameter("uid",userId).list();
    }
    public Optional<Beneficiary> findByIdAndOwnerId(Long id,Long userId){
        return session().createQuery("FROM Beneficiary WHERE id=:id AND owner.id=:uid",Beneficiary.class)
                .setParameter("id",id).setParameter("uid",userId).uniqueResultOptional();
    }
    public void delete(Beneficiary b){session().delete(b);}
}
