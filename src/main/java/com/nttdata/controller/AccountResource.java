package com.nttdata.controller;

import com.nttdata.model.dto.AccountResponseDto;
import com.nttdata.service.AccountService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class AccountResource {

    private AccountService accountService;

    @Inject
    JsonWebToken jwt;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    public Uni<List<AccountResponseDto>> listAccounts() {
        String documentNumber = jwt.getSubject();
        return accountService.listAccounts(documentNumber);
    }

}
