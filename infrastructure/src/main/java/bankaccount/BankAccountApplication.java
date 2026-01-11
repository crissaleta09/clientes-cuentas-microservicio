
package bankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;

/**
 * Main class for Bank Account Application
 * @author isabel.saletameza
 */
@SpringBootApplication
@ComponentScan(basePackages = {"bankaccount"})
public class BankAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAccountApplication.class, args);
	}

}
