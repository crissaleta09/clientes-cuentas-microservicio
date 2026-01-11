package bankAccount.application.service;

import org.springframework.stereotype.Service;

import bankAccount.domain.model.CuentaBancaria;
import bankAccount.domain.repository.BankAccountRepository;

/**
 * @Service class for bank account management
 * @author isabel.saletameza
 */
@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Method to create a bank account
     * @param newBankAccount the bank account to be created
     * @return returns the created bank account
     */
    public CuentaBancaria createAccount(CuentaBancaria newBankAccount) {
        return bankAccountRepository.createAccount(newBankAccount);
    }

    /**
     * Method to update only the amount of a bank account
     * @param newValues object that containsthe new values for the bank account
     * @return the updated bank account
     */
	public CuentaBancaria updateAccount(CuentaBancaria newValues) {
        return bankAccountRepository.updateAccount(newValues);
	}

}
