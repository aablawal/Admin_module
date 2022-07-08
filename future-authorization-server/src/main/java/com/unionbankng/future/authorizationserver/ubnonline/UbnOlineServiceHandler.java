package com.unionbankng.future.authorizationserver.ubnonline;

import com.unionbankng.future.authorizationserver.ad.FakeX509TrustManager;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import javax.xml.ws.BindingProvider;
import java.security.KeyManagementException;

public class UbnOlineServiceHandler extends WebServiceGatewaySupport {

	static {
		java.net.Authenticator.setDefault(new java.net.Authenticator() {
			@Override
			protected java.net.PasswordAuthentication getPasswordAuthentication() {
				return new java.net.PasswordAuthentication("marcus", "marcus123".toCharArray());
			}
		});
	}

	public static OutputParameters doOnlineUbnCheck(String bvn) throws KeyManagementException {
		FakeX509TrustManager fk = new FakeX509TrustManager();
		fk.allowAllSSL();
		ONLINECUSTOMERVALIDATIONPttBindingQSService service = new ONLINECUSTOMERVALIDATIONPttBindingQSService();
		ONLINECUSTOMERVALIDATIONPtt port = service.getONLINECUSTOMERVALIDATIONPttBindingQSPort();
		// Set timeout until a connection is established
		((BindingProvider) port).getRequestContext().put("javax.xml.ws.client.connectionTimeout", "5000");
		// Set timeout until the response is received
		((BindingProvider) port).getRequestContext().put("javax.xml.ws.client.receiveTimeout", "5000");
		// for tracing inbound and outbound

		ObjectFactory factory = new ObjectFactory();
		JAXBElement<String> createBvn = factory.createInputParametersBVN(bvn);
		InputParameters input = new InputParameters();
		input.setBVN(createBvn);

		return port.onlineCUSTOMERVALIDATION(input);

		
	}

//	public static void main(String[] args) throws MalformedURLException, KeyManagementException {
//
//		System.out.println("+++++" + doOnlineUbnCheck("22178939555").getCUSTOMERID().getValue().getCustList().get(0)
//				.getCustid().get(0).getValue() + "+++++++++");
//	}

}
