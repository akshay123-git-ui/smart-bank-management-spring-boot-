package com.smartbank.dao;
import com.smartbank.entity.Card;
import java.util.Optional;
public interface CardDao { Card save(Card c); Optional<Card> findByUserId(Long userId); }
