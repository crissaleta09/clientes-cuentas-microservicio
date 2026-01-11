package bankAccount.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bankAccount.domain.model.Cliente;
import bankAccount.domain.repository.CustomerRepository;

/**
 * @Service class for customer management
 * @author isabel.saletameza
 */
@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	
	public CustomerService (CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	/**
	 * Method to get all customers
	 * @return list of all customers
	 */
	public List<Cliente> getAllCustomers() {
		return customerRepository.getAllCustomers();
	}

	/**
	 * Method to get all adult customers
	 * @return list of adult customers
	 */
	public List<Cliente> getAdultCustomers() {
		return customerRepository.getAdultCustomers();
	}

	/**
	 * Method to get customers with total account amount greater than specified amount
	 * @param amount the amount to compare
	 * @return list of customers with total account amount greater than specified amount
	 */
	public List<Cliente> getCustomersGreaterThanAmount(Double amount) {
		return customerRepository.getCustomersGreaterThanAmount(amount);
	}

	
	/**
	 * Method to get a customer by dni
	 * @param dni the dni of the customer
	 * @return the customer with the specified dni
	 */
	public Cliente getCustomerByDni(String dni) {
		return customerRepository.getCustomerByDni(dni);
	}

}
