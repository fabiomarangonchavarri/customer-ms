package com.nttdata.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

@ApplicationScoped
public class PasswordService {

    private static final int MEMORY = 65536;       // 64 MB
    private static final int ITERATIONS = 3;
    private static final int PARALLELISM = 1;
    private static final int HASH_LENGTH = 32;
    private static final int SALT_LENGTH = 16;

    private final SecureRandom secureRandom = new SecureRandom();

    public String hash(String password) {

        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);

        byte[] hash = generateHash(
                password.toCharArray(),
                salt
        );

        return "$argon2id$v=19"
                + "$m=" + MEMORY
                + ",t=" + ITERATIONS
                + ",p=" + PARALLELISM
                + "$" + Base64.getEncoder().encodeToString(salt)
                + "$" + Base64.getEncoder().encodeToString(hash);
    }
    public boolean verify(String password, String encodedHash) {

        String[] parts = encodedHash.split("\\$");

        if (parts.length != 6) {
            return false;
        }

        // parts[0] = ""
        // parts[1] = argon2id
        // parts[2] = v=19
        // parts[3] = m=65536,t=3,p=1
        // parts[4] = salt
        // parts[5] = hash

        String[] parameters = parts[3].split(",");

        int memory = Integer.parseInt(parameters[0].split("=")[1]);
        int iterations = Integer.parseInt(parameters[1].split("=")[1]);
        int parallelism = Integer.parseInt(parameters[2].split("=")[1]);

        byte[] salt = Base64.getDecoder().decode(parts[4]);
        byte[] expectedHash = Base64.getDecoder().decode(parts[5]);

        Argon2Parameters argon2Parameters =
                new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                        .withSalt(salt)
                        .withMemoryAsKB(memory)
                        .withIterations(iterations)
                        .withParallelism(parallelism)
                        .build();

        Argon2BytesGenerator generator = new Argon2BytesGenerator();

        generator.init(argon2Parameters);

        byte[] actualHash = new byte[expectedHash.length];

        generator.generateBytes(
                password.toCharArray(),
                actualHash,
                0,
                actualHash.length
        );

        return MessageDigest.isEqual(
                actualHash,
                expectedHash
        );
    }


    private byte[] generateHash(char[] password, byte[] salt) {

        Argon2Parameters parameters =
                new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                        .withSalt(salt)
                        .withMemoryAsKB(MEMORY)
                        .withIterations(ITERATIONS)
                        .withParallelism(PARALLELISM)
                        .build();

        Argon2BytesGenerator generator = new Argon2BytesGenerator();

        generator.init(parameters);

        byte[] hash = new byte[HASH_LENGTH];

        generator.generateBytes(
                password,
                hash,
                0,
                hash.length
        );

        return hash;
    }
}