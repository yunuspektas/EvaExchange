package com.example.repository;

import com.example.entity.PortfolioStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortoFolioStockRepository extends JpaRepository<PortfolioStock, Long> {
}
