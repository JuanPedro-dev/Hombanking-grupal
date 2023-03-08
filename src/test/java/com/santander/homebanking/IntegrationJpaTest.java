package com.santander.homebanking;

import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IntegrationJpaTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientLoansRepository clientLoansRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CreditCardTransactionRepository  creditCardTransactionRepository;

    @Autowired
    DebitCardTransactionRepository debitCardTransactionRepository;


    @MockBean
    PasswordEncoder passwordEncoder;


    @Test
    void testFindAllClients(){
        List<Client> clients = clientRepository.findAll();
        assertFalse(clients.isEmpty());
    }

    @Test
    void testFindClientById() {
        Optional<Client> client = clientRepository.findById(1L);
        assertTrue(client.isPresent());
        assertEquals("admin", client.orElseThrow().getFirstName());
    }

    @Test
    void testFindAccountAll(){
        List<Account> accounts = accountRepository.findAll();
        assertFalse(accounts.isEmpty());
    }

    @Test
    void testFindAccountById(){
        Optional<Account> account = accountRepository.findById(1L);
        assertTrue(account.isPresent());
        assertEquals("VIN001", account.orElseThrow().getNumber());
    }

    @Test
    void testFindAllTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertFalse(transactions.isEmpty());
    }

    @Test
    void testFindTransactionById(){
        Optional<Transaction> transaction = transactionRepository.findById(1L);
        assertTrue(transaction.isPresent());
        assertEquals(TransactionType.CREDIT, transaction.orElseThrow().getType());
    }

    @Test
    void testFindAllLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertFalse(loans.isEmpty());
    }

    @Test
    void testFindLoanById(){
        Optional<Loan> loan = loanRepository.findById(1L);
        assertTrue(loan.isPresent());
        assertEquals("Hipotecario", loan.orElseThrow().getName());
    }

    @Test
    void testFindAllClientLoans(){
        List<ClientLoan> clientLoans = clientLoansRepository.findAll();
        assertFalse(clientLoans.isEmpty());
    }

    @Test
    void testFindClientLoanById(){
        Optional<ClientLoan> clientLoan = clientLoansRepository.findById(1L);
        assertTrue(clientLoan.isPresent());
        assertEquals(400000, clientLoan.orElseThrow().getAmount());
        assertEquals(60, clientLoan.orElseThrow().getPayments());
    }

    @Test
    void testClientSave(){
        Client data = new Client("Juan Pedro", "Trionfini", "juanpedro.developer@gmail.com", "password");
        Client client = clientRepository.save(data);

        assertEquals("Juan Pedro", client.getFirstName());
        assertEquals("Trionfini", client.getLastName());
        assertEquals("juanpedro.developer@gmail.com", client.getEmail());
        assertEquals("password", client.getPassword());
    }

    @Test
    void testAccountSave(){
        Client client = clientRepository.findById(1L).get();
        Account data = new Account("VIN001", LocalDate.now(), 5000.0);
        data.setClient(client);
        Account account = accountRepository.save(data);

        assertEquals("VIN001", account.getNumber());
        assertEquals(5000, account.getBalance());
        assertEquals("admin", account.getClient().getFirstName());
    }

    @Test
    void testFindAllCreditTransaction(){
        List<CreditCardTransaction> creditCardTransactions = creditCardTransactionRepository.findAll();
        assertFalse(creditCardTransactions.isEmpty());
    }

    @Test
    void testFindCreditById(){
        Optional<CreditCardTransaction> creditCardTransactions = creditCardTransactionRepository.findById(1L);
        assertTrue(creditCardTransactions.isPresent());
        assertEquals("1111-2222-3333-4444", creditCardTransactions.orElseThrow().getCreditCard().getNumber());
    }



}
