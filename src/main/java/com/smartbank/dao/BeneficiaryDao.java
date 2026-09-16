package com.smartbank.dao;
import com.smartbank.entity.Beneficiary;
import java.util.List;
import java.util.Optional;
public interface BeneficiaryDao {
    Beneficiary save(Beneficiary b);
    List<Beneficiary> findByOwnerId(Long userId);
    Optional<Beneficiary> findByIdAndOwnerId(Long id, Long userId);
    void delete(Beneficiary b);
}
