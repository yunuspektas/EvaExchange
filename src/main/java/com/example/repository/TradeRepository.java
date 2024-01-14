package com.example.repository;

import com.example.entity.Trade;
import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    @Query("FROM Trade t WHERE t.user=?1 ")
    Page<Trade> findAllByUser(User customer, Pageable pageable);

}
