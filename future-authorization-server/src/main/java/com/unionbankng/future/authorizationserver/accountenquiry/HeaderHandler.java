package com.unionbankng.future.authorizationserver.accountenquiry;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

	public boolean handleMessage(SOAPMessageContext smc) {

		Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (outboundProperty.booleanValue()) {

			SOAPMessage message = smc.getMessage();

			try {

				SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
				envelope.addAttribute(envelope.createName("xmlns:ubn"),
						"http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader");
				envelope.addAttribute(envelope.createName("xmlns:gat"), "http://gateway.union.com/");

				SOAPHeader header = envelope.getHeader();

				SOAPHeaderElement ubnHeader = header.addHeaderElement(header.createQName("UBNHeader", "ubn"));

				ubnHeader.addChildElement(ubnHeader.createQName("MessageId", "ubn"));
				ubnHeader.addChildElement(ubnHeader.createQName("CorrelationId", "ubn"));
				ubnHeader.addChildElement(ubnHeader.createQName("InternalMessageId", "ubn"));
				ubnHeader.addChildElement(ubnHeader.createQName("ClientId", "ubn"));
				ubnHeader.addChildElement(ubnHeader.createQName("Service", "ubn"));
				ubnHeader.addChildElement(ubnHeader.createQName("Operation", "ubn"));
				ubnHeader.addChildElement(ubnHeader.createQName("RequestedTimestamp", "ubn"));
				ubnHeader.addChildElement(ubnHeader.createQName("ResponseTimestamp", "ubn"));
				SOAPElement business = ubnHeader.addChildElement(ubnHeader.createQName("BusinessKeyPairs", "ubn"));
				business.addChildElement(new QName("ubn", "BusinessKey"));
				business.setAttribute("name", "");

				message.saveChanges();
				// Print out the outbound SOAP message to System.out
				message.writeTo(System.out);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			try {

				// we just print out the SOAP message.
				SOAPMessage message = smc.getMessage();
				message.writeTo(System.out);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return outboundProperty;

	}

	public boolean handleFault(SOAPMessageContext context) {
		// throw new UnsupportedOperationException("Not supported yet.");
		return true;
	}

	public void close(MessageContext context) {
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}
}