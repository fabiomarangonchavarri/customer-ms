package com.nttdata.controller;

import com.nttdata.model.dto.ExchangeRateDto;
import com.nttdata.service.ExchangeRateService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Optional;

@Path("/exchange-rate")
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class ExchangeRateResource {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateResource(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GET
    public Uni<Optional<ExchangeRateDto>> getLastExchangeRate() {
        return exchangeRateService.getLastExchangeRate();
    }

}
