package com.nttdata.controller;

import com.azure.core.annotation.Patch;
import com.nttdata.model.Sample;
import com.nttdata.service.SampleService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/sample")
public class SampleResource {

    private final SampleService sampleService;

    public SampleResource(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Sample>> getAllSamples() {
        return sampleService.findAll();
    }

}
