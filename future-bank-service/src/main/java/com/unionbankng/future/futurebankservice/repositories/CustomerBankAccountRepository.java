package com.unionbankng.future.futurebankservice.repositories;

import com.unionbankng.future.futurebankservice.entities.CustomerBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerBankAccountRepository extends JpaRepository<CustomerBankAccount,Long> {

    List<CustomerBankAccount> findAllByUserUUID(String userUUID);
    List<CustomerBankAccount> findAllByCustomerUBNId(Long ubnId);
    Optional<CustomerBankAccount> findByAccountNumber(String accountNumber);
    Boolean existsByAccountNumber(String accountNumber);
}
