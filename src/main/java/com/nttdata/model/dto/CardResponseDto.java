package com.nttdata.model.dto;

public record CardResponseDto(String id, String cardNumber, Double totalBalance, Double usedBalance, Double remainingBalance, Double usedBalancePEN, Double usedBalanceUSD) {
}
