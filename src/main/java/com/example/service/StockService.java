package com.example.service;

import com.example.dto.request.StockRequest;
import com.example.entity.Stock;
import com.example.exception.ConflictException;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.mappers.StockMapper;
import com.example.payload.messages.ErrorMessages;
import com.example.payload.messages.SuccessMessages;
import com.example.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public Stock checkStockWithSymbol(String stockSymbol){
        return findBySymbol(stockSymbol);
    }

    public Stock findBySymbol(String stockSymbol) {
        return stockRepository.findBySymbol(stockSymbol)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_STOCK_WITH_SYMBOL, stockSymbol)));
    }

    public String saveStock(StockRequest stockRequest){
        // Symbol degeri unique mi kontrolu :
        if(stockRepository.existsBySymbol(stockRequest.getSymbol())) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_STOCK_SYMBOL_MESSAGE, stockRequest.getSymbol()));
        }
        Stock stock = stockMapper.mapStockRequestToStock(stockRequest);
        stockRepository.save(stock);
        return SuccessMessages.SAVE_STOCK_MESSAGE;
    }

    public int countStock(){
        return stockRepository.countStock();
    }
}
