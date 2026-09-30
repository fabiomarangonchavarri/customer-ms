package com.nttdata.repository;

import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosContainer;
import com.nttdata.config.CosmosContainerFactory;
import com.nttdata.model.Sample;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@ApplicationScoped
public class SampleRepository {

    private final CosmosAsyncContainer container;

    public SampleRepository(CosmosContainerFactory factory,
                            @ConfigProperty(name = "cosmos.containers.sampleContainer") String containerName) {
        this.container = factory.getContainer(containerName);
    }

    public Uni<List<Sample>> findAll() {

        String query = """
               SELECT c.id, c.categoryId, c.categoryName, c.sku, c.name, c.description, c.price, c.tags FROM c
               """;

        return Uni.createFrom()
                .completionStage(
                        container.queryItems(query, Sample.class)
                                .collectList()
                                .toFuture()
                );

    }

}
