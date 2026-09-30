package com.nttdata.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ExchangeRate {
    private String id;
    private String pair;
    private Double buyRate;
    private Double sellRate;
    private OffsetDateTime timestamp;
}
