package com.nttdata.service;

import com.nttdata.model.dto.AccountResponseDto;
import com.nttdata.model.dto.ExchangeRateDto;
import com.nttdata.repository.AccountRepository;
import com.nttdata.repository.ExchangeRateRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public Uni<Optional<ExchangeRateDto>> getLastExchangeRate() {
        return exchangeRateRepository.getLastExchangeRate();
    }

}
