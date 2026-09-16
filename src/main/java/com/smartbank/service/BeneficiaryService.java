package com.smartbank.service;
import com.smartbank.entity.Beneficiary;
import java.util.List;
public interface BeneficiaryService {
    List<Beneficiary> list(Long userId);
    void add(Long userId,String name,String accountNumber,String ifsc,String upiId);
    void remove(Long userId,Long id);
}
