package bankaccount.out.repository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import bankAccount.domain.model.Cliente;
import bankAccount.domain.model.CuentaBancaria;
import bankAccount.domain.repository.BankAccountRepository;
import bankAccount.domain.repository.CustomerRepository;
import bankaccount.out.adapter.BankAccountJpaRepository;
import bankaccount.out.entity.ClienteEntity;
import bankaccount.out.entity.CuentaBancariaEntity;

@Repository
public class BankAccountRepositoryImpl implements BankAccountRepository {
    private final BankAccountJpaRepository bankAccountJpaRepository;
    private final CustomerRepository customerRepository;
    public BankAccountRepositoryImpl(BankAccountJpaRepository bankAccountJpaRepository, CustomerRepository customerRepository) {
        this.bankAccountJpaRepository = bankAccountJpaRepository;
        this.customerRepository = customerRepository;
    }

	@Override
	public CuentaBancaria createAccount(CuentaBancaria newBankAccount) {
		Cliente customer = customerRepository.getCustomerByDni(newBankAccount.getDniCliente());
		if (customer == null) {
			customerRepository.createCustomer(newBankAccount.getDniCliente());
		}
		CuentaBancariaEntity bankAccountEntity = toEntity(newBankAccount);
		CuentaBancariaEntity saved = bankAccountJpaRepository.save(bankAccountEntity);
		return toDomain(saved);
	}

	@Override
	public CuentaBancaria updateAccount(CuentaBancaria newValues) {
		CuentaBancariaEntity entity = bankAccountJpaRepository.findById(newValues.getId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The bank account doesn't exist"));
		entity = mergeNewValuesBankAccount(entity,toEntity(newValues));
		CuentaBancariaEntity saved = bankAccountJpaRepository.save(entity);
		return toDomain(saved);
	}
	
	/**
	 * Metodo para poder mergear los datos nuevos en la cuenta bancaria
	 * Nota: solo se puede actualizar tipo de cuenta y total
	 * Id no se actualiza por ser la clave
	 * DNI no se actualiza ya que si la cuenta cambia de dueño es mejor crear una con un id nuevo
	 * @param oldBankAccount
	 * @param newBankAccount
	 * @return
	 */
	private CuentaBancariaEntity mergeNewValuesBankAccount(CuentaBancariaEntity oldBankAccount, CuentaBancariaEntity newBankAccount) {
		if (newBankAccount.getTipoCuenta()!=null) oldBankAccount.setTipoCuenta(newBankAccount.getTipoCuenta());
		if (newBankAccount.getTotal()!=null) oldBankAccount.setTotal(newBankAccount.getTotal());
		return oldBankAccount;
	}

	public CuentaBancaria getAccountById(Long id) {
		return bankAccountJpaRepository.findById(id).map(this::toDomain).orElse(null);
	}
	
	private CuentaBancariaEntity toEntity(CuentaBancaria bankAccount) {
		CuentaBancariaEntity entity = new CuentaBancariaEntity();
		ClienteEntity customer = new ClienteEntity();
		customer.setDni(bankAccount.getDniCliente());
		entity.setTipoCuenta(bankAccount.getTipoCuenta());
		entity.setTotal(bankAccount.getTotal() != null ? bankAccount.getTotal() : 0.0);
		entity.setCliente(customer);
		return entity;
	}
	
	private CuentaBancaria toDomain (CuentaBancariaEntity entity) {
		CuentaBancaria bankAccount = new CuentaBancaria();
		bankAccount.setId(entity.getId());
		bankAccount.setDniCliente(entity.getCliente().getDni());
		bankAccount.setTipoCuenta(entity.getTipoCuenta());
		bankAccount.setTotal(entity.getTotal());
		return bankAccount;
	}
}
