package com.nttdata.repository;

import com.azure.cosmos.CosmosAsyncContainer;
import com.nttdata.config.CosmosContainerFactory;
import com.nttdata.model.dto.AccountResponseDto;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@ApplicationScoped
public class AccountRepository {

    private final CosmosAsyncContainer container;

    public AccountRepository(CosmosContainerFactory factory,
                             @ConfigProperty(name = "cosmos.containers.accounts") String containerName) {
        this.container = factory.getContainer(containerName);
    }

    public Uni<List<AccountResponseDto>> listAccounts(String documentNumber) {

        String query = String.format("SELECT c.accountNumber, c.accountType, c.currency, c.balance, c.lastUpdated FROM c WHERE c.customerDocumentNumber = '%s' AND c.status = 'A'", documentNumber);

        return Uni.createFrom()
                .completionStage(
                        container.queryItems(query, AccountResponseDto.class)
                                .collectList()
                                .toFuture()
                );
    }

}
