package b13.service;

import java.util.List;
import java.util.Optional;

import b13.dto.Payment;

public interface PaymentService {
	Optional<Payment> insertPayment(Payment payment);
	
	boolean deletePaymentById(long paymentId);
	
	Optional<Payment> getPaymentById(long paymentId);
	
	Optional<Payment> updatePayment(Payment payment);
	
	Optional<List<Payment>> getPaymentsByUsername(String username, int offset, int limit);
}
