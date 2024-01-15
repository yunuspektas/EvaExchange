package com.example.controller;

import com.example.dto.request.TradeRequest;
import com.example.dto.response.TradeResponse;
import com.example.entity.Trade;
import com.example.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {


    private final TradeService tradeService;

    @PostMapping("/buy") // http://localhost:8080/api/trades/buy + JSON
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<Trade> buyStock(@RequestBody @Valid TradeRequest tradeRequest,
                                          HttpServletRequest httpServletRequest) {
        try {
            Trade trade = tradeService.executeBuy(
                    httpServletRequest,
                    tradeRequest.getStockSymbol(),
                    tradeRequest.getQuantity()
            );
            return new ResponseEntity<>(trade, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sell") // http://localhost:8080/api/trades/sell + JSON
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<Trade> sellStock(@RequestBody @Valid TradeRequest tradeRequest,
                                           HttpServletRequest httpServletRequest) {
        try {
            Trade trade = tradeService.executeSell(
                   httpServletRequest,
                    tradeRequest.getStockSymbol(),
                    tradeRequest.getQuantity()
            );
            return new ResponseEntity<>(trade, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")// http://localhost:8080/api/trades/getAll?page=1&size=10
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public List<TradeResponse> getAllTrade(HttpServletRequest httpServletRequest) {
       return tradeService.getAllTrade(httpServletRequest);
    }
}

