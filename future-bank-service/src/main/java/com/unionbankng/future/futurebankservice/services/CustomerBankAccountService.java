package com.unionbankng.future.futurebankservice.services;

import com.unionbankng.future.futurebankservice.entities.CustomerBankAccount;
import com.unionbankng.future.futurebankservice.repositories.CustomerBankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerBankAccountService {

    private final CustomerBankAccountRepository customerBankAccountRepository;


    public CustomerBankAccount save (CustomerBankAccount customerBankAccount){
        return customerBankAccountRepository.save(customerBankAccount);
    }

    public Boolean existsByAccountNumber(String accountNumber){
        return customerBankAccountRepository.existsByAccountNumber(accountNumber);
    }

    public Optional<CustomerBankAccount> findById(Long id){
        return customerBankAccountRepository.findById(id);
    }

    public Optional<CustomerBankAccount> findByAccountNumber(String accountNumber){
        return customerBankAccountRepository.findByAccountNumber(accountNumber);
    }

    public List<CustomerBankAccount> findAllByUserUUID(String userUUID){
        return customerBankAccountRepository.findAllByUserUUID(userUUID);
    }

    public List<CustomerBankAccount> findAllByCustomerUBNId(Long ubnId){
        return customerBankAccountRepository.findAllByCustomerUBNId(ubnId);
    }


}
