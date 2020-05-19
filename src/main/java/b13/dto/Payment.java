package b13.dto;

import java.io.Serializable;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("orderId")
	long orderId;
	@JsonProperty("cardNumber")
	long cardNumber;
	@JsonProperty("ccv")
	int ccv;
	@JsonProperty("orderTotal")
	double orderTotal;
	
}
