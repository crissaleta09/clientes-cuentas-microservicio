package bankaccount.domain.repository;

import java.util.List;

import bankaccount.domain.model.Cliente;

public interface CustomerRepository {
	
	/** 
	 * Method to get all customers
	 * @return list of all customers
	 */
	List<Cliente> getAllCustomers();

	/** 
	 * Method to get all adult customers
	 * @return list of adult customers
	 */
	List<Cliente> getAdultCustomers();

	/** 
	 * Method to get customers with total account amount greater than specified amount
	 * @param amount the amount to compare
	 * @return list of customers with total account amount greater than specified amount
	 */
	List<Cliente> getCustomersGreaterThanAmount(Double amount);
	
	/** 
	 * Method to get a customer by dni
	 * @param dni the dni of the customer
	 * @return the customer with the specified dni
	 */
	Cliente getCustomerByDni( String dni);
	
	/** 
	 * Method to create a customer
	 * @param dni the dni of the customer to be created
	 * @return the created customer
	 */
	Cliente createCustomer(String dni);
}
