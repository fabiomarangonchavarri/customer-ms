package com.nttdata.service;

import com.nttdata.model.Sample;
import com.nttdata.repository.SampleRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SampleService {

    private final SampleRepository sampleRepository;

    public SampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    public Uni<List<Sample>> findAll() {
        return sampleRepository.findAll();
    }

}
