package com.example.payload.mappers;

import com.example.dto.request.StockRequest;
import com.example.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public Stock mapStockRequestToStock(StockRequest stockRequest){
        return Stock.builder()
                .rate(stockRequest.getRate())
                .currentPrice(stockRequest.getCurrentPrice())
                .symbol(stockRequest.getSymbol())
                .build();
    }
}
