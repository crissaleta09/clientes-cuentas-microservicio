package bankaccount.in.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import bankaccount.domain.model.Cliente;
import bankaccount.application.service.CustomerService;

/**
 * @author isabel.saletameza
 * Api Rest controller for customer management
 */
@RestController
@RequestMapping("/clientes")
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * Method to get all customers
	 * @return list of all customers
	 */
	@GetMapping()
	public List<Cliente> getAllCustomers() {
		List<Cliente> customers = customerService.getAllCustomers();
		return customers;
	}

	/**
	 * Method to get all adult customers
	 * @return list of adult customers
	 */
	@GetMapping("/mayores-de-edad")
	public List<Cliente> getAdultCustomers() {
		List<Cliente> customers = customerService.getAdultCustomers();
		return customers;
	}

	/**
	 * Method to get customers with total account amount greater than specified amount
	 * @param amount the amount to compare
	 * @return list of customers with total account amount greater than specified amount
	 */
	@GetMapping("con-cuenta-superior-a/{cantidad}")
	public List<Cliente> getCustomersGreaterThanAmount(@PathVariable("cantidad") Double amount) {
		if (amount == null || amount <= 0.0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must specify an amount greater than 0");
		}
		List<Cliente> customers = customerService.getCustomersGreaterThanAmount(amount);
		return customers;
	}
	
	/**
	 * Method to get a customer by dni
	 * @param dni the dni of the customer
	 * @return the customer with the specified dni
	 */
	@GetMapping("/{dni}")
	public Cliente getCustomerByDni(@PathVariable String dni) {
		if (dni == null || dni.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must specify a valid dni");
		}
		Cliente customer = customerService.getCustomerByDni(dni);
		return customer;
	}
}
