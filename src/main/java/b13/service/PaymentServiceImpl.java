package b13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import b13.dao.PaymentRepository;
import b13.dao.util.Pager;
import b13.dto.Payment;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService{

	PaymentRepository paymentRepo;

	@Override
	public Optional<Payment> insertPayment(Payment payment) {
		return Optional.of(paymentRepo.save(payment)); 
	}

	@Override
	public boolean deletePaymentById(long paymentId) {
		Optional<Payment> retVal = paymentRepo.findById(paymentId);
		if(!retVal.isPresent()) {
			paymentRepo.deleteById(paymentId);
			return true;
		}
		return false;
	}

	@Override
	public Optional<Payment> getPaymentById(long paymentId) {
		return paymentRepo.findById(paymentId);
	}

	@Override
	public Optional<Payment> updatePayment(Payment payment) {
		return Optional.of(paymentRepo.save(payment));
	}

	@Override
	public Optional<List<Payment>> getPaymentsByUsername(String username, int offset, int limit) {
		return Optional.ofNullable(paymentRepo.findByUsername(username, Pager.of(offset, limit)).getContent());
	}
	
	
}
