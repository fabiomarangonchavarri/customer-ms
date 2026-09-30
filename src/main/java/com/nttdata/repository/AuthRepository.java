package com.nttdata.repository;

import com.azure.cosmos.CosmosAsyncContainer;
import com.nttdata.config.CosmosContainerFactory;
import com.nttdata.model.dto.PasswordHashDto;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;

@ApplicationScoped
public class AuthRepository {

    private final CosmosAsyncContainer container;

    public AuthRepository(CosmosContainerFactory factory,
                          @ConfigProperty(name = "cosmos.containers.customers") String containerName) {
        this.container = factory.getContainer(containerName);
    }

    public Uni<Optional<PasswordHashDto>> getPasswordHashByDocumentNumber(String documentNumber) {

        String query = String.format("SELECT c.email, c.roles, c.passwordHash FROM c WHERE c.documentNumber = '%s' AND c.status = 'A'", documentNumber);

        return Uni.createFrom()
                .completionStage(
                        container.queryItems(query, PasswordHashDto.class)
                                .collectList()
                                .toFuture()
                                .thenApply(list -> list.isEmpty() ? Optional.empty() : Optional.of(list.get(0)))
                );
    }



}
