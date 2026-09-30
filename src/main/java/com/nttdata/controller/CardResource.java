package com.nttdata.controller;

import com.nttdata.model.dto.AccountResponseDto;
import com.nttdata.model.dto.CardResponseDto;
import com.nttdata.service.AccountService;
import com.nttdata.service.CardService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/cards")
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class CardResource {

    private CardService cardService;

    @Inject
    JsonWebToken jwt;

    public CardResource(CardService cardService) {
        this.cardService = cardService;
    }

    @GET
    public Uni<List<CardResponseDto>> listCards() {
        String documentNumber = jwt.getSubject();
        return cardService.listCards(documentNumber);
    }

}
