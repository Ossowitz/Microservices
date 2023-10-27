package com.jkrotal.processing.repository;

import com.jkrotal.processing.model.AccountEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
    List<AccountEntity> findByUserId(Long userId);
}