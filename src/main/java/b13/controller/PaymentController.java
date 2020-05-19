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
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Order;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import b13.dto.OrderSummary;
import lombok.AllArgsConstructor;

@RequestMapping("/payments")
@RestController
@AllArgsConstructor
public class PaymentController {
	
	String clientId = "AXqwt5xKBqFi_Ojw0UedKDIsjI0q5NBMj3z52vHPEwf-CLv1p-flc4LFDd9kcaEYcRcPGqcq2nnSB9NE";
	String secretKey = "EEz1TTKJdGj5NnOIdeGwxr2DXAuS3Yr7bvL109H9dwx1rSHBybEp6hsOWdSLf4httZ3VlwCQYzc7yz7W";
	
	
//	@PutMapping(value = "/{paymentId}")
//	public ResponseEntity<?> updateUser(HttpServletRequest request, @PathVariable long paymentId, @RequestBody Payment payment){
//		payment.setPaymentId(paymentId);
//		return service.updatePayment(payment).map(p -> ResponseEntity.status(HttpStatus.OK)
//				.header("Location", request.getRequestURI() + "/" + p.getPaymentId()).build())
//				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build());
//	}
	
	@RabbitListener(queues = {"payment-receiver"})
	public ResponseEntity<?> createPayment(OrderSummary order){
		
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(order.getGrandtotal().toString());
		
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		Payment payment = new Payment();
		payment.setIntent("authorize");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("redirect:/orders/" + order.getOrderid());
//		redirectUrls.setReturnUrl("redirect");
		payment.setRedirectUrls(redirectUrls);
		Payment createdPayment;
		String approvalurl = "";
		try {
			APIContext context = new APIContext(clientId, secretKey, "sandbox");
			createdPayment = payment.create(context);
			if(createdPayment != null) {
				List<Links> links = createdPayment.getLinks();
				for (Links link: links) {
					if(link.getRel().equals("approval_url")) {
						approvalurl = link.getHref();
						break;
					}
				}
			}
			
		}
		catch(PayPalRESTException e) {
			
		}
		
		
		if(orderAmount > totalAmount) {
			throw new AmqpRejectAndDontRequeueException("User amount does not cover order amount");
		}
	}
	//TODO taking online card details, retrying 
	
}
