package com.santander.homebanking;

import com.santander.homebanking.models.*;
import com.santander.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class HomebankingApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  CardRepository cardRepository,
									  CreditCardRepository creditCardRepository,
									  DebitCardRepository debitCardRepository,
									  AccountRepository accountRepository,
									  LoanRepository loanRepository,
									  ClientLoansRepository clientLoansRepository,
									  TransactionRepository transactionRepository,
									  InterestRateRepository interestRateRepository,
									  CreditCardTransactionRepository creditCardTransactionRepository
	){
		return (args) -> {

			Client client_custom = new Client("Juan Pedro", "Trionfini", "juanpedro.developer@gmail.com", passwordEncoder.encode("password"));
			Client client_admin = new Client("admin", "admin", "admin@admin", passwordEncoder.encode("admin"));

			Account account1 = new Account("VIN001", LocalDate.parse("2023-01-01"), 100000.0);
			Account account2 = new Account("VIN002", LocalDate.parse("2023-01-02"), 40000.0);
			Account account3 = new Account("VIN003", LocalDate.parse("2023-01-03"), 12000.0);
			Account account4 = new Account("VIN004", LocalDate.parse("2023-01-04"), 36000.0);

			LocalDate localDate1 = LocalDate.parse("2022-09-08");
			LocalDateTime localDateTime1 = localDate1.atStartOfDay();

			Transaction t1 = new Transaction(2000.0, "Transferencia recibida", localDateTime1, TransactionType.CREDIT);
			Transaction t2 = new Transaction(4000.0, "Compra tienda xx", localDateTime1, TransactionType.DEBIT);
			Transaction t3 = new Transaction(1000.0, "Transferencia recibida", localDateTime1, TransactionType.CREDIT);
			Transaction t4 = new Transaction(200.0, "Compra tienda xx", localDateTime1, TransactionType.DEBIT);
			Transaction t5 = new Transaction(8000.0, "Transferencia recibida", localDateTime1, TransactionType.CREDIT);
			Transaction t6 = new Transaction(2000.0, "Compra tienda xx", localDateTime1, TransactionType.DEBIT);
			Transaction t7 = new Transaction(700.0, "Transferencia recibida", localDateTime1, TransactionType.CREDIT);
			Transaction t8 = new Transaction(2000.0, "Compra tienda xx", localDateTime1, TransactionType.DEBIT);

			account1.addTransactions(t1);
			account1.addTransactions(t2);
			account2.addTransactions(t3);
			account2.addTransactions(t4);
			account3.addTransactions(t5);
			account3.addTransactions(t6);
			account4.addTransactions(t7);
			account4.addTransactions(t8);

			account1.setClient(client_custom);
			account2.setClient(client_custom);
			account3.setClient(client_admin);
			account4.setClient(client_admin);

			client_custom.addAccounts(account1);
			client_custom.addAccounts(account2);
			client_admin.addAccounts(account3);
			client_admin.addAccounts(account3);
			client_admin.addAccounts(account4);

			Loan loan1 = new Loan("Hipotecario", 500000.0);
			Loan loan2 = new Loan("Personal", 100000.0);
			Loan loan3 = new Loan("Automotriz", 300000.0);

			loan1.addPayments(12);
			loan1.addPayments(24);
			loan1.addPayments(36);
			loan1.addPayments(48);
			loan1.addPayments(60);

			loan2.addPayments(6);
			loan2.addPayments(12);
			loan2.addPayments(24);

			loan3.addPayments(6);
			loan3.addPayments(12);
			loan3.addPayments(24);
			loan3.addPayments(36);

			ClientLoan clientLoan1 = new ClientLoan(400000.0, 60, client_custom, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000.0, 12, client_custom, loan2);
			ClientLoan clientLoan3 = new ClientLoan(100000.0, 24, client_admin, loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000.0, 36, client_admin, loan3);


			CreditCard creditCard_custom = new CreditCard(
					"Juan Pedro Trionfini",
					"1111-2222-3333-4444",
					123,
					LocalDate.parse("2023-01-01"),
					LocalDate.parse("2027-09-08"),
					CardColor.TITANIUM,
					CardType.CREDIT,
					"1234",
					200000.0,
					200000.0);

			client_custom.addCreditCard(creditCard_custom);

			CreditCard creditCard_admin = new CreditCard("Admin Admin", "0000-0000-0000-0000", 163, LocalDate.parse("2023-01-01"), LocalDate.parse("2027-09-08"), CardColor.TITANIUM, CardType.CREDIT, "1234", 200000.0, 200000.0);
			client_admin.addCreditCard(creditCard_admin);

			DebitCard debitCard = new DebitCard("Juan Pedro Trionfini", "2222-3333-4444-5555", 123, LocalDate.parse("2023-01-01"), LocalDate.parse("2027-09-08"), CardColor.GOLD, CardType.DEBIT, "1234");
			account1.addDebitCard(debitCard);


			Double baseInterestRate = 0.0;
			for (int i = 1; i < 25; i++){
				baseInterestRate += 0.005;
				baseInterestRate = new BigDecimal(baseInterestRate).setScale(3, RoundingMode.HALF_UP).doubleValue();
				InterestRate interestRate = new InterestRate(i, baseInterestRate);
				interestRateRepository.save(interestRate);
			}

			clientRepository.save(client_admin);
			clientRepository.save(client_custom);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			creditCardRepository.save(creditCard_admin);
			creditCardRepository.save(creditCard_custom);

			CreditCardTransaction creditCardTransaction1 = new CreditCardTransaction(100000.0, "Televisor Samsung 50 pulgadas", LocalDateTime.now(),
					"123456", Status.PASSED, 12, 0.05, creditCard_custom);

			CreditCardTransaction creditCardTransaction2 = new CreditCardTransaction(280000.0, "Celular Samsung S23", LocalDateTime.now(),
					"123456", Status.PASSED, 24, 0.08, creditCard_custom);

			CreditCardTransaction creditCardTransaction3 = new CreditCardTransaction(3800.0, "Supermercado ", LocalDateTime.now(),
					"123456", Status.PASSED, 3, 0.03, creditCard_custom);


			creditCardTransactionRepository.save(creditCardTransaction1);
			creditCardTransactionRepository.save(creditCardTransaction2);
			creditCardTransactionRepository.save(creditCardTransaction3);

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			clientLoansRepository.save(clientLoan1);
			clientLoansRepository.save(clientLoan2);
			clientLoansRepository.save(clientLoan3);

			transactionRepository.save(t1);
			transactionRepository.save(t2);
			transactionRepository.save(t3);
			transactionRepository.save(t4);
			transactionRepository.save(t5);
			transactionRepository.save(t6);
			transactionRepository.save(t7);
			transactionRepository.save(t8);

		};
	}
}
