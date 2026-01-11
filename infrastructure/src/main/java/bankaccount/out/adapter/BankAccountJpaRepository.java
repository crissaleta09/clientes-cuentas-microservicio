package bankaccount.out.adapter;

import org.springframework.data.jpa.repository.JpaRepository;

import bankaccount.out.entity.CuentaBancariaEntity;


/**
 * JPA Repository interface for BankAccountEntity
 * @author isabel.saletameza
 */
public interface BankAccountJpaRepository extends JpaRepository<CuentaBancariaEntity, Long> {
}
