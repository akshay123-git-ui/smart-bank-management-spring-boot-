package com.smartbank.service;
import com.smartbank.dao.CardDao;
import com.smartbank.dao.UserDao;
import com.smartbank.entity.Card;
import com.smartbank.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.UUID;
@Service
public class CardServiceImpl implements CardService {
    @Autowired private CardDao cardDao; @Autowired private UserDao userDao;
    @Transactional public Card getOrCreate(Long userId){
        return cardDao.findByUserId(userId).orElseGet(()->{
            User u=userDao.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
            String digits=Math.abs(UUID.randomUUID().hashCode())+"";
            while(digits.length()<8) digits="0"+digits;
            String last=digits.substring(0,4);
            String masked="XXXX-XXXX-XXXX-"+last;
            return cardDao.save(new Card(u,masked,last,LocalDate.now().plusYears(5)));
        });
    }
}
