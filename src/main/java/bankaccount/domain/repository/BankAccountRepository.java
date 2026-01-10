package bankaccount.domain.repository;

import bankaccount.domain.model.CuentaBancaria;

public interface BankAccountRepository {

    /** 
     * Method to create a bank account
     * @param newBankAccount the bank account to be created
     * @return returns the created bank account
     */
    CuentaBancaria createAccount(CuentaBancaria newBankAccount);

	/** 
     * Method to update only the amount of a bank account
     * @param newValues object that containsthe new values for the bank account
     * @return the updated bank account
     */
	CuentaBancaria updateAccount(CuentaBancaria newValues);
}
