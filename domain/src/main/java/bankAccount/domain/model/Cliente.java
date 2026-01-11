package bankaccount.domain.model;

import java.time.LocalDate;
import java.util.List;
/**
 * Client record model for bank account management
 * @author isabel.saletameza
 *
 */
public record Cliente(String dni, String nombre, String apellido1, String apellido2, LocalDate fechaNacimiento, List<CuentaBancaria> cuentas) {
	
}