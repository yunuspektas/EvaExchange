package com.example.repository;

import com.example.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findBySymbol(String stockSymbol);

    boolean existsBySymbol(String symbol);

    @Query("SELECT COUNT(s) FROM Stock s")
    int countStock();

}
