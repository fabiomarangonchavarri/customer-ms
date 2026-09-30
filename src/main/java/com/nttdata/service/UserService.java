package com.nttdata.service;

import com.nttdata.model.User;
import com.nttdata.repository.UserRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Uni<List<User>> findAll() {
        return userRepository.findAll();
    }

    public Uni<User> findById(String id, String partitionKey) {
        return userRepository.findById(id, partitionKey);
    }

    public Uni<User> create(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreatedAt(OffsetDateTime.now());
        return userRepository.create(user);
    }

    public Uni<User> update(User user) {
        return userRepository.update(user);
    }

    public Uni<Boolean> delete(String id, String partitionKey) {
        return userRepository.delete(id, partitionKey);
    }

}
