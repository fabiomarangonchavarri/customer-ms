package com.nttdata.model.dto;

public record CardDto(
        String id,
        String customerDocumentNumber,
        String cardNumber,
        Double totalBalance,
        Double usedBalancePEN,
        Double usedBalanceUSD) {
}
