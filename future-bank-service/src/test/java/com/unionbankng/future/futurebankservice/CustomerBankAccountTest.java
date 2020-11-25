package com.unionbankng.future.futurebankservice;

import com.unionbankng.future.futurebankservice.entities.CustomerBankAccount;
import com.unionbankng.future.futurebankservice.enums.AccountStatus;
import com.unionbankng.future.futurebankservice.services.CustomerBankAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerBankAccountTest extends AbstractTest {

    @Autowired
    CustomerBankAccountService customerBankAccountService;


    @Test
    public void testAccountCreation()  {

        CustomerBankAccount customerBankAccount = new CustomerBankAccount();
        customerBankAccount.setUserUUID("33333-44444-444");
        customerBankAccount.setCustomerUBNId(1l);
        customerBankAccount.setAccountName("Okeme Baba");
        customerBankAccount.setBranchCode("000");
        customerBankAccount.setAccountType("SA_01");
        customerBankAccount.setAccountNumber("00222334444");
        customerBankAccount.setAccountStatus(AccountStatus.PAYMENT_CONFIRMED);

        customerBankAccount = customerBankAccountService.save(customerBankAccount);

        assertEquals("33333-44444-444",customerBankAccount.getUserUUID());


    }

    @Test
    public void findByUserUUID()  {

        CustomerBankAccount customerBankAccount = new CustomerBankAccount();
        customerBankAccount.setUserUUID("33333-44444-444");
        customerBankAccount.setCustomerUBNId(2l);
        customerBankAccount.setAccountName("Okeme Baba");
        customerBankAccount.setBranchCode("000");
        customerBankAccount.setAccountType("SA_01");
        customerBankAccount.setAccountNumber("00222334434");
        customerBankAccount.setAccountStatus(AccountStatus.PAYMENT_CONFIRMED);

        customerBankAccountService.save(customerBankAccount);

        List<CustomerBankAccount> accounts = customerBankAccountService.findAllByUserUUID("33333-44444-444");

        assertTrue(accounts.size() > 0);


    }
}
