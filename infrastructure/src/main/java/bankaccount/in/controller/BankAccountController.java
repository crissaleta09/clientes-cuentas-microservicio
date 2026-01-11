package bankaccount.in.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import bankAccount.application.service.BankAccountService;
import bankAccount.domain.model.CuentaBancaria;

/**
 * Api Rest controller for bank account management
 * @author isabel.saletameza
 *
 */
@RestController
@RequestMapping("/cuentas")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /**
     * Method to create a bank account
     * @param newAccount the bank account to be created
     * @return returns the created bank account
     */
    @PostMapping
    public ResponseEntity<CuentaBancaria> createAccount(@RequestBody CuentaBancaria newAccount ) {
    	AssertAccountCrationRequest(newAccount);
        CuentaBancaria accountCreated = bankAccountService.createAccount(newAccount);
        return ResponseEntity.ok(accountCreated);
    }


    /**
     * Method to update only the amount of a bank account
     * @param newValues object that containsthe new values for the bank account
     * @param id the id of the bank account to be updated
     * @return returns the updated bank account
     */
    @PutMapping("/{idCuenta}")
    public ResponseEntity<CuentaBancaria> updateAccountAmount(@RequestBody CuentaBancaria newValues, @PathVariable("idCuenta") Long id ) {
    	if (id<=0 || newValues.getDniCliente()!=null || newValues.getTipoCuenta()!=null) { //limitar la actualización de datos a solamente el total
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You provide incorrect data for this method");
    	}
    	newValues.setId(id);
    	CuentaBancaria accountUpdated = bankAccountService.updateAccount(newValues);
        return ResponseEntity.ok(accountUpdated);
    }

	/**
	 * Method to validate the request to create a bank account
	 */
	private void AssertAccountCrationRequest(CuentaBancaria newAccount) {
    	if (newAccount.getDniCliente() ==null || newAccount.getTipoCuenta() ==null|| newAccount.getDniCliente().isEmpty() || newAccount.getTipoCuenta().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must specify requiered fields");
    	}
    	if (newAccount.getTotal()!=null && newAccount.getTotal() < 0.0) { //si se especifica cantidad, debe ser mayor a 0, si no se especifica se establece 0 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't create account with negative balance");
    	}
	}

}
