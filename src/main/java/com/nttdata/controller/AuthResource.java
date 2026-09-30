package com.nttdata.controller;

import com.nttdata.model.dto.AuthRequestDto;
import com.nttdata.model.dto.PasswordHashDto;
import com.nttdata.service.AuthService;
import com.nttdata.service.PasswordService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Optional;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final AuthService authService;
    private final PasswordService passwordService;

    @Inject
    JsonWebToken jwt;

    public AuthResource(AuthService authService, PasswordService passwordService) {
        this.authService = authService;
        this.passwordService = passwordService;
    }

    @POST
    @Path("/encodePassword")
    public String encodePassword(@QueryParam("password") String password) {
        return passwordService.hash(password);
    }

    @POST
    @Path("/login")
    public Uni<Response> login(AuthRequestDto authRequestDto) {
        return authService
                .login(authRequestDto)
                .onItem()
                .transform(authResponseDto -> {
                    if (authResponseDto.success()) {
                        return Response.ok(authResponseDto).build();
                    } else {
                        return Response.status(Response.Status.UNAUTHORIZED).entity(authResponseDto).build();
                    }
                });
    }

    @GET
    @Path("/test")
    @RolesAllowed("CUSTOMER")
    public Response test() {
        String documentId = jwt.getSubject();
        return Response.ok(documentId).build();
    }

}
