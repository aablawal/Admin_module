package com.unionbankng.future.authorizationserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = AuthorizationServerApplication.class,properties = {
		"grpc.server.port=-1" // Disable external server
})
class AuthorizationServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
