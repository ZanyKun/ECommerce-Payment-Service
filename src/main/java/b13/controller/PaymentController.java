package b13.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Order;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import b13.dto.OrderSummary;
import b13.service.PayPalClient;
import lombok.AllArgsConstructor;

@RequestMapping("/payments")
@RestController
@AllArgsConstructor
public class PaymentController {
	
	PayPalClient payPalClient;
		
//	@PutMapping(value = "/{paymentId}")
//	public ResponseEntity<?> updateUser(HttpServletRequest request, @PathVariable long paymentId, @RequestBody Payment payment){
//		payment.setPaymentId(paymentId);
//		return service.updatePayment(payment).map(p -> ResponseEntity.status(HttpStatus.OK)
//				.header("Location", request.getRequestURI() + "/" + p.getPaymentId()).build())
//				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build());
//	}
	
	@RabbitListener(queues = {"payment-receiver"})
	public ResponseEntity<?> createPayment(OrderSummary order){
		String status = payPalClient.createPayment(order);
		
		return null;
	}
	
	@GetMapping("/complete_payment")
	public ResponseEntity<?> completePayment(){
		String status = payPalClient.completePayment();
		
		return null;
	}
	
}
