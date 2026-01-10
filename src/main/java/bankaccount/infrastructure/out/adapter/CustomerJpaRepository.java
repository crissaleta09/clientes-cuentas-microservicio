package bankaccount.infrastructure.out.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import bankaccount.infrastructure.out.entity.ClienteEntity;

/**
 * JPA Repository interface for CustomerEntity
 * @author isabel.saletameza
 */
public interface CustomerJpaRepository extends JpaRepository<ClienteEntity, String>, JpaSpecificationExecutor<ClienteEntity>  {
	/** Custom method to find all customers with bank accounts
	 * @return list of customers with bank accounts
	 */
	@Query("SELECT c FROM ClienteEntity c LEFT JOIN FETCH c.cuentas")
	List<ClienteEntity> findClientesConCuentasBancarias();
}
