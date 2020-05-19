package b13.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import b13.dto.OrderSummary;

@Service
public class PayPalClient {
	
	String clientId = "AXqwt5xKBqFi_Ojw0UedKDIsjI0q5NBMj3z52vHPEwf-CLv1p-flc4LFDd9kcaEYcRcPGqcq2nnSB9NE";
	String secretKey = "EEz1TTKJdGj5NnOIdeGwxr2DXAuS3Yr7bvL109H9dwx1rSHBybEp6hsOWdSLf4httZ3VlwCQYzc7yz7W";
	
	public String createPayment(OrderSummary order){
		
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(order.getGrandtotal().toString());
		amount.setDetails(new Details().setSubtotal(order.getSubtotal().toString())
				.setTax(order.getTax().toString()).setShipping(order.getShippingCost().toString()));
		
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
			createdPayment = payment.create(context);	//Throws PayPalRESTException
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
			throw new AmqpRejectAndDontRequeueException("There was a problem processing the payment.");
		}
		
		return null;
	}
	//TODO taking online card details, retrying 
	
	@GetMapping("/complete_payment")
	public String completePayment(){
		return null;
	}
	
}
