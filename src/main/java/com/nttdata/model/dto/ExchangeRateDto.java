package com.nttdata.model.dto;

public record ExchangeRateDto(
    String pair,
    Double buyRate,
    Double sellRate
) {
}
