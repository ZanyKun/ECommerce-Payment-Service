package b13;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.BindingBuilder.DirectExchangeRoutingKeyConfigurer;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}
	
	@Bean
	public DirectExchange exchange() {
		return ExchangeBuilder.directExchange("initial-exchange").durable(false).build();
	}
	
	@Bean
	public DirectExchange dlxexchange() {
		return ExchangeBuilder.directExchange("retry-payment").durable(false).build();
	}
	
	@Bean
	public Queue queue1() {
		return QueueBuilder.nonDurable("payment-receiver").deadLetterExchange("try-payment").build();
	}
	
	@Bean
	public Queue queue2() {
		return QueueBuilder.nonDurable("retrying-initial-exchange").deadLetterExchange("initial-exchange").ttl(10000).build();
	}
	
	@Bean
	public DirectExchangeRoutingKeyConfigurer binding1() {
		return BindingBuilder.bind(queue1()).to(exchange());
	}
	
	@Bean
	public DirectExchangeRoutingKeyConfigurer binding2() {
		return BindingBuilder.bind(queue2()).to(dlxexchange());
	}
}
