package bankaccount.infrastructure.out.adapter;

import org.springframework.data.jpa.repository.JpaRepository;

import bankaccount.infrastructure.out.entity.CuentaBancariaEntity;

import java.util.List;
import java.util.UUID;

/**
 * JPA Repository interface for BankAccountEntity
 * @author isabel.saletameza
 */
public interface BankAccountJpaRepository extends JpaRepository<CuentaBancariaEntity, Long> {
}
