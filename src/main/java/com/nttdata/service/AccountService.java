package com.nttdata.service;

import com.nttdata.model.dto.AccountResponseDto;
import com.nttdata.repository.AccountRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Uni<List<AccountResponseDto>> listAccounts(String documentNumber) {
        return accountRepository.listAccounts(documentNumber);
    }

}
