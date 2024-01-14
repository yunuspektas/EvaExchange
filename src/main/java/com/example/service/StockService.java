package com.example.service;

import com.example.entity.Stock;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public Stock checkStockWithSymbol(String stockSymbol){
        return stockRepository.findBySymbol(stockSymbol)
                .orElseThrow(() -> new RuntimeException("Hisse senedi bulunamadı: " + stockSymbol));

    }

    public Stock findBySymbol(String stockSymbol) {
        return stockRepository.findBySymbol(stockSymbol).orElseThrow(()->
                new ResourceNotFoundException("Stock bulunamadi"));
    }
}
