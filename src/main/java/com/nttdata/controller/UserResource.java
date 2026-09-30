package com.nttdata.controller;

import com.nttdata.model.User;
import com.nttdata.service.UserService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.awt.*;
import java.util.List;

@Path("/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<User>> findAll() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<User> findById(@PathParam("id") String id, @QueryParam("partitionKey") String partitionKey) {
        return userService.findById(id, partitionKey);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<User> create(User user) {
        return userService.create(user);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<User> update(@PathParam("id") String id, User user) {
        user.setId(id);
        return userService.update(user);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> delete(@PathParam("id") String id, @QueryParam("partitionKey") String partitionKey) {
        return userService.delete(id, partitionKey);
    }

}