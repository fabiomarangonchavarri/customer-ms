package com.nttdata.repository;

import com.azure.cosmos.CosmosAsyncContainer;
import com.nttdata.config.CosmosContainerFactory;
import com.nttdata.model.dto.AccountResponseDto;
import com.nttdata.model.dto.CardDto;
import com.nttdata.model.dto.CardResponseDto;
import com.nttdata.service.ExchangeRateService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CardRepository {

    private final CosmosAsyncContainer container;

    public CardRepository(CosmosContainerFactory factory,
                          @ConfigProperty(name = "cosmos.containers.cards") String containerName, ExchangeRateService exchangeRateService) {
        this.container = factory.getContainer(containerName);
    }

    public Uni<List<CardDto>> listCards(String documentNumber) {

        String query = String.format("SELECT c.id, c.customerDocumentNumber, c.cardNumber, c.totalBalance, c.usedBalancePEN, c.usedBalanceUSD FROM c WHERE c.customerDocumentNumber = '%s'", documentNumber);

        return Uni.createFrom()
                .completionStage(
                        container.queryItems(query, CardDto.class)
                                .collectList()
                                .toFuture()
                );

    }

}
