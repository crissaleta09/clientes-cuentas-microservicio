
package bankaccount.infrastructure.out.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

/**
 * Entity class for Customer
 * @author isabel.saletameza
 */
@Entity
@Table(name = "cliente")
public class ClienteEntity {
    @Id
	private String dni; // DNI as primary key
    private String nombre;
    private String apellido1;
    private String apellido2;
    private LocalDate fecha_nacimiento;
    
    @OneToMany(mappedBy="cliente") // One customer can have multiple bank accounts
    private List<CuentaBancariaEntity> cuentas;
    
    public ClienteEntity() {}
    public ClienteEntity(String dni, String nombre, String apellido1, String apellido2, LocalDate fecha_nacimiento, List<CuentaBancariaEntity> cuentas) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fecha_nacimiento = fecha_nacimiento;
        this.cuentas = cuentas;
    }
    public String getDni() { return dni; }
    public void setDni(String dni) {
    	this.dni = dni;
    }
    public String getNombre() { return nombre; }
    public String getApellido1() { return apellido1; }
    public String getApellido2() { return apellido2; }
    public LocalDate getFechaNacimiento() { return fecha_nacimiento; }
    public List<CuentaBancariaEntity> getCuentas() {return cuentas;}

}
