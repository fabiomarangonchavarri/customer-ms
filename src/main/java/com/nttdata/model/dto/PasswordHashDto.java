package com.nttdata.model.dto;

import java.util.List;

public record PasswordHashDto(String email, List<String> roles, String passwordHash) {
}
