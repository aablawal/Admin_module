package com.unionbankng.future.futuremessagingservice;

import com.unionbankng.future.futuremessagingservice.interfaces.EmailProvider;
import com.unionbankng.future.futuremessagingservice.pojos.EmailBody;
import com.unionbankng.future.futuremessagingservice.smsandemailproviders.UnionEmailProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

@SpringBootApplication
//@EnableJms
public class FutureMessagingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FutureMessagingServiceApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

//	@Bean
//	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
//													DefaultJmsListenerContainerFactoryConfigurer configurer) {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		// This provides all boot's default to this factory, including the message converter
//		configurer.configure(factory, connectionFactory);
//		// You could still override some of Boot's default if necessary.
//		return factory;
//	}

	@Bean
	public Consumer<String> sendEmail(){
		return emailRequest ->{
			try {
				System.out.println("Received message: "+ emailRequest);
//				EmailBody emailBody = mapper.readValue(emailRequest, EmailBody.class);
//				logger.info("Received message: {}", emailBody.getSubject());
//				EmailProvider emailProvider = new UnionEmailProvider();
//				emailProvider.send(processEmailTemplate(emailBody));
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

}
