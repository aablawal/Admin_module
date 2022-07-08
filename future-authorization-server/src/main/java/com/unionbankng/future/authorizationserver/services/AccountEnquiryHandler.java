package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.accountenquiryprod.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountEnquiryHandler {

	private static Logger logger = Logger.getLogger(AccountEnquiryHandler.class.getName());

	static {
		java.net.Authenticator.setDefault(new java.net.Authenticator() {
			@Override
			protected java.net.PasswordAuthentication getPasswordAuthentication() {
				return new java.net.PasswordAuthentication("marcus", "marcus123".toCharArray());
			}
		});
	}


	public static CustomerAcctsInfo getAccountSummaryProd(String customerId) throws Exception {

		logger.log(Level.INFO, "customer enquiry prod.... ");

		FakeX509TrustManager fk = new FakeX509TrustManager();
		fk.allowAllSSL();

		EnquiryServiceOmniflowService service = new EnquiryServiceOmniflowService();
		HeaderHandlerResolver handlerResolver = new HeaderHandlerResolver();
		service.setHandlerResolver(handlerResolver);
		EnquiryService port = service.getEnquiryServicePort();



		GetAccountSummaryByCustomerID request = new GetAccountSummaryByCustomerID();
		request.setCustomerID(customerId);

		return port.getAccountSummaryByCustomerID(customerId);



	}
}
