package bankAccount.domain.model;

/* Intenté utilizar lombok pero por problemas con el IDE preferí no utlizarlo 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CuentaBancaria {
	private Long id;
	private String dniCliente;
	private String tipoCuenta;
	private Double total;
}
*/
public class CuentaBancaria {
	private Long id;
	private String dniCliente;
	private String tipoCuenta;
	private Double total;

	public CuentaBancaria() {
	}

	public CuentaBancaria(Long id, String dniCliente, String tipoCuenta, Double total) {
		this.id = id;
		this.dniCliente = dniCliente;
		this.tipoCuenta = tipoCuenta;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDniCliente() {
		return dniCliente;
	}

	public void setDniCliente(String dniCliente) {
		this.dniCliente = dniCliente;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}