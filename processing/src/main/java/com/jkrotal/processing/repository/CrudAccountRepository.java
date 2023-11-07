package com.jkrotal.processing.repository;

import com.jkrotal.processing.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudAccountRepository extends JpaRepository<AccountEntity, Long> {

}