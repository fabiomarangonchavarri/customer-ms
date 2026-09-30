package com.nttdata.service;

import com.nttdata.model.dto.AuthRequestDto;
import com.nttdata.model.dto.AuthResponseDto;
import com.nttdata.model.dto.PasswordHashDto;
import com.nttdata.repository.AuthRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordService passwordService;
    private final JwtService jwtService;

    public AuthService(AuthRepository authRepository, PasswordService passwordService, JwtService jwtService) {
        this.authRepository = authRepository;
        this.passwordService = passwordService;
        this.jwtService = jwtService;
    }

    public Uni<AuthResponseDto> login(AuthRequestDto authRequestDto) {
        return authRepository.getPasswordHashByDocumentNumber(authRequestDto.documentNumber())
                .onItem()
                .transform(optionalHash -> {
                    if (optionalHash.isEmpty()) {
                        return new AuthResponseDto(false, null);
                    }

                    if (passwordService.verify(authRequestDto.password(), optionalHash.get().passwordHash())) {
                        return new AuthResponseDto(true, jwtService.generateToken(authRequestDto.documentNumber(), optionalHash.get().email(), optionalHash.get().roles()));
                    }

                    return new AuthResponseDto(false, null);
                });
    }

}
