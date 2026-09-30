package com.nttdata.repository;

import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.PartitionKey;
import com.nttdata.config.CosmosContainerFactory;
import com.nttdata.model.Sample;
import com.nttdata.model.User;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {
    private final CosmosAsyncContainer container;

    public UserRepository(CosmosContainerFactory factory,
                            @ConfigProperty(name = "cosmos.containers.usersContainer") String containerName) {
        this.container = factory.getContainer(containerName);
    }

    public Uni<List<User>> findAll() {

        String query = """
               SELECT c.id, c.userId, c.name, c.email, c.birthDate, c.salary, c.children, c.active, c.tags, c.createdAt FROM c
               """;

        return Uni.createFrom()
                .completionStage(
                        container.queryItems(query, User.class)
                                .collectList()
                                .toFuture()
                );

    }

    public Uni<User> findById(
            String id,
            String partitionKey) {

        return Uni.createFrom()
                .completionStage(
                        container
                                .readItem(
                                        id,
                                        new PartitionKey(partitionKey),
                                        User.class
                                )
                                .map(CosmosItemResponse::getItem)
                                .toFuture()
                );
    }

    public Uni<User> create(User user) {

        return Uni.createFrom()
                .completionStage(
                        container
                                .createItem(user)
                                .toFuture()
                )
                .chain(response ->
                        Uni.createFrom()
                                .completionStage(
                                        container
                                                .readItem(
                                                        user.getId(),
                                                        new PartitionKey(user.getUserId()),
                                                        User.class
                                                )
                                                .toFuture()
                                )
                )
                .map(CosmosItemResponse::getItem);
    }

    public Uni<User> update(User user) {

        return Uni.createFrom()
                .completionStage(
                        container
                                .upsertItem(user)
                                .toFuture()
                )
                .chain(() ->
                        Uni.createFrom()
                                .completionStage(
                                        container
                                                .readItem(
                                                        user.getId(),
                                                        new PartitionKey(user.getUserId()),
                                                        User.class
                                                )
                                                .toFuture()
                                )
                )
                .map(CosmosItemResponse::getItem);
    }

    public Uni<Boolean> delete(
            String id,
            String partitionKey) {

        return Uni.createFrom()
                .completionStage(
                        container
                                .deleteItem(
                                        id,
                                        new PartitionKey(partitionKey)
                                )
                                .toFuture()
                )
                .map(response -> true)
                .onFailure(CosmosException.class)
                .recoverWithItem(throwable -> {
                    CosmosException exception = (CosmosException) throwable;

                    if (exception.getStatusCode() == 404) {
                        return false;
                    }

                    throw exception;
                });
    }
}
