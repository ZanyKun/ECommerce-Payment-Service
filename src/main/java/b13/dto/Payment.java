package b13.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long paymentId;
	long orderId;
	String username;
	long cardNumber;
	double orderTotal;
	Date orderDate;
	PaymentStatus paymentStatus;
	
	public enum PaymentStatus{
		PAYMENT_PROCESSING, PAYMENT_CONFIRMED, PAYMENT_REFUNDED, PAYMENT_CANCELLED;
	}
}
