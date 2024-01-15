package com.example.controller;

import com.example.dto.request.StockRequest;
import com.example.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping // http://localhost:8080/api/stock + JSON
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> saveStock(@RequestBody @Valid StockRequest stockRequest){
        return new ResponseEntity<>(stockService.saveStock(stockRequest), HttpStatus.CREATED);
    }
}
