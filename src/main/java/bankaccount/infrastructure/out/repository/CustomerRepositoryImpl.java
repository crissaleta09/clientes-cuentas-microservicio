package bankaccount.infrastructure.out.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import bankaccount.domain.model.Cliente;
import bankaccount.domain.model.CuentaBancaria;
import bankaccount.domain.repository.CustomerRepository;
import bankaccount.infrastructure.out.adapter.CustomerJpaRepository;
import bankaccount.infrastructure.out.entity.ClienteEntity;
import bankaccount.infrastructure.out.entity.CuentaBancariaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

	private final CustomerJpaRepository customerJpaRepository;

	public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository) {
		this.customerJpaRepository = customerJpaRepository;
	}

	@Override
	public List<Cliente> getAllCustomers() {
		return customerJpaRepository.findClientesConCuentasBancarias().stream().map(this::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Cliente> getAdultCustomers() {
		LocalDate actualDate = LocalDate.now();
		LocalDate limit = actualDate.minusYears(18);
		Specification<ClienteEntity> spec = Specification
				.where((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fecha_nacimiento"), limit));
		return customerJpaRepository.findAll(spec).stream().map(this::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Cliente> getCustomersGreaterThanAmount(Double amount) {
		Specification<ClienteEntity> spec = (Root<ClienteEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
	        Subquery<Double> subquery = query.subquery(Double.class);
	        var cuenta = subquery.from(CuentaBancariaEntity.class);
	        subquery.select(cb.sum(cuenta.get("total")));
	        subquery.where(cb.equal(cuenta.get("cliente"), root));
	        return cb.greaterThan(subquery, amount); // yo hubiera puesto greaterThanOrEqualTo pero el criterio es > cantidad
	    };
	    
	    return customerJpaRepository.findAll(spec).stream().map(this::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Cliente getCustomerByDni(String dni) {
		return customerJpaRepository.findById(dni).map(this::toDomain).orElse(null);
	}
	

	@Override
	public Cliente createCustomer(String dni) {
		ClienteEntity customer = new ClienteEntity();
		customer.setDni(dni);
		ClienteEntity saved = customerJpaRepository.save(customer);
		return toDomain(saved);
	}
	
	private Cliente toDomain(ClienteEntity entity) {
		List<CuentaBancaria> cuentas = bankAccountToDomain(entity.getCuentas());
		return new Cliente(entity.getDni(), entity.getNombre(), entity.getApellido1(), entity.getApellido2(),
				entity.getFechaNacimiento(), cuentas);
	}

	private ClienteEntity toEntity(Cliente customer) {
		List<CuentaBancariaEntity> cuentas = bankAccountToEntity(customer.cuentas());
		return new ClienteEntity(customer.dni(), customer.nombre(),customer.apellido1(),customer.apellido2(), customer.fechaNacimiento(),cuentas);
	}

	private List<CuentaBancaria> bankAccountToDomain(List<CuentaBancariaEntity> cuentaBancariaEntityList) {
		if (cuentaBancariaEntityList == null)
			return List.of();
		return cuentaBancariaEntityList.stream()
				.map(entity -> new CuentaBancaria(
						entity.getId(),
						entity.getCliente() != null ? entity.getCliente().getDni() : null, entity.getTipoCuenta(),
						entity.getTotal()))
				.collect(Collectors.toList());
	}
	
	private List<CuentaBancariaEntity> bankAccountToEntity(List<CuentaBancaria> cuentaBancariaList) {
		if (cuentaBancariaList == null)
			return List.of();
		return cuentaBancariaList.stream()
				.map(cuenta -> {
					CuentaBancariaEntity entity = new CuentaBancariaEntity();
					entity.setId(cuenta.getId());
					entity.setTipoCuenta(cuenta.getTipoCuenta());
					entity.setTotal(cuenta.getTotal());
					return entity;
				})
				.collect(Collectors.toList());
	}
}
