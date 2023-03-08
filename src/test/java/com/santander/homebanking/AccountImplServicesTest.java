package com.santander.homebanking;

import com.santander.homebanking.dtos.AccountDTO;
import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.repositories.AccountRepository;
import com.santander.homebanking.repositories.CardRepository;
import com.santander.homebanking.repositories.ClientRepository;
import com.santander.homebanking.services.implement.AccountImplService;
import com.santander.homebanking.services.implement.ClientImplService;
import com.santander.homebanking.utils.ResponseUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AccountImplServicesTest{

        ClientRepository clientRepository = mock(ClientRepository.class);
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountImplService accountImplService = new AccountImplService(clientRepository, accountRepository);

        List<Client> clients = Arrays.asList(
                new Client("Juan Pedro", "Trionfini", "juanpedro.developer@gmail.com", "password"),
                new Client("admin", "admin", "admin@admin", "admin")
        );

        List<Account> accounts = Arrays.asList(
                new Account("VIN001", LocalDate.parse("2023-01-01"), 100000.0),
                new Account("VIN002", LocalDate.parse("2023-01-01"), 40000.0)
        );

        @Before
        public void inicData(){
                clients.get(0).addAccounts(accounts.get(0));
                clients.get(1).addAccounts(accounts.get(1));
        }


        @Test
        public void getAccounts(){
                when(accountRepository.findAll()).thenReturn(accounts);
                Set<AccountDTO> accountDTOSet = accountImplService.getAccounts();
                Assert.assertEquals(accounts.size(), accountDTOSet.size());
        }



}
