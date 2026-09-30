package com.nttdata.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    private final PrivateKey privateKey;

    public JwtService(
            @ConfigProperty(name = "jwt.private-key")
            String privateKeyBase64) {
        System.out.print(privateKeyBase64);
        this.privateKey = loadPrivateKey(privateKeyBase64);
    }

    public String generateToken(
            String customerId,
            String email,
            List<String> roles) {

        return Jwt
                .issuer("banking-auth")
                .subject(customerId)
                .upn(email)
                .groups(Set.copyOf(roles))
                .expiresIn(300)
                .sign(privateKey);
    }

    private PrivateKey loadPrivateKey(String privateKeyBase64) {

        try {
            byte[] keyBytes = Base64
                    .getDecoder()
                    .decode(privateKeyBase64);

            PKCS8EncodedKeySpec keySpec =
                    new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory keyFactory =
                    KeyFactory.getInstance("RSA");

            return keyFactory.generatePrivate(keySpec);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Could not load JWT private key",
                    e
            );
        }
    }
}