package com.nttdata.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public record AccountResponseDto(
    String accountNumber,
    String accountType,
    String currency,
    Double balance,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") OffsetDateTime lastUpdated
) {
}