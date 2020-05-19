package b13.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummary implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("orderid")
	long orderid;
	
	@JsonProperty("subtotal")
	BigDecimal subtotal;
	
	@JsonProperty("grandtotal")
	BigDecimal grandtotal;
	
	@JsonProperty("tax")
	BigDecimal tax;
	
	@JsonProperty("shippingCost")
	BigDecimal shippingCost;
}
