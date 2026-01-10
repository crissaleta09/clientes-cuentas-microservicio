package bankaccount.domain.model;

import java.time.LocalDate;
import java.util.List;

public record Cliente(String dni, String nombre, String apellido1, String apellido2, LocalDate fechaNacimiento, List<CuentaBancaria> cuentas) {
	
}