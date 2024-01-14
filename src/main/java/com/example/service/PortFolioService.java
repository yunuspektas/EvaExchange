package com.example.service;

import com.example.entity.Portfolio;
import com.example.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortFolioService {

    private final PortfolioRepository portfolioRepository;

    public Portfolio checkPortfolioById(Long portfolioId){
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portföy bulunamadı: " + portfolioId));
    }


}
