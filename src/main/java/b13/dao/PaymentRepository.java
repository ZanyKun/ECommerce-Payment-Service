package b13.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import b13.dto.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

	Page<Payment> findByUsername(String username, Pageable page);
}
