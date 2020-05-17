package b13.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import b13.dto.Payment;
import b13.service.PaymentService;
import lombok.AllArgsConstructor;

@RequestMapping("/payments")
@RestController
@AllArgsConstructor
public class PaymentController {

	PaymentService service;
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<?> createPayment(@RequestBody Payment payment, HttpServletRequest request){
		return service.insertPayment(payment)
				.map(p -> ResponseEntity.status(HttpStatus.CREATED)
						.header("Location", request.getRequestURI() + "/" + p.getPaymentId())
						.build())
						.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build());
	}
	
	@GetMapping(value = "/{paymentId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<?> getPayment(@PathVariable long paymentId){
		return ResponseEntity.of(service.getPaymentById(paymentId));
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<Payment>> getPaymentsByUsername(@RequestParam(name = "username") String username, @RequestParam(name = "offset", defaultValue = "0") int offset,
													 @RequestParam(name = "limit", defaultValue = "20") int limit){
		return service.getPaymentsByUsername(username, offset, limit)
				.map(p -> ResponseEntity.status(HttpStatus.OK).body(p))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@DeleteMapping(value = "/{paymentId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Payment> deletePayment(@PathVariable long paymentId){
		if(service.deletePaymentById(paymentId))
			return ResponseEntity.status(HttpStatus.OK).build();
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PutMapping(value = "/{paymentId}")
	public ResponseEntity<?> updateUser(HttpServletRequest request, @PathVariable long paymentId, @RequestBody Payment payment){
		payment.setPaymentId(paymentId);
		return service.updatePayment(payment).map(p -> ResponseEntity.status(HttpStatus.OK)
				.header("Location", request.getRequestURI() + "/" + p.getPaymentId()).build())
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build());
	}
	
}
