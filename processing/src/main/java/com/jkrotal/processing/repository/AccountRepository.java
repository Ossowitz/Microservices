package com.jkrotal.processing.repository;

import com.jkrotal.processing.model.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private static final Sort SORT_ID = Sort.by(Sort.Direction.ASC, "id");

    private final CrudAccountRepository repository;

    public AccountEntity save(AccountEntity account) {
        return repository.save(account);
    }

    public Optional<AccountEntity> findById(Long accountId) {
        return repository.findById(accountId);
    }

    public List<AccountEntity> getAll() {
        return repository.findAll(SORT_ID);
    }
}