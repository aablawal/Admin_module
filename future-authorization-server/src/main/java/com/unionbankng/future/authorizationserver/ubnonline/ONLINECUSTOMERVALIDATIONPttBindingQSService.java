package com.unionbankng.future.authorizationserver.ubnonline;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * OSB Service
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */

@WebServiceClient(name = "newibankbvn_ptt-bindingQSService", targetNamespace = "http://xmlns.oracle.com/pcbpel/adapter/db/DigitalServices/newibankbvnservice/newibankbvn", wsdlLocation = "https://unionworkspace.unionbankng.com:443/bvnnewibankservice/bvnnewibankcheck")

public class ONLINECUSTOMERVALIDATIONPttBindingQSService
    extends Service
{

    private final static URL ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_WSDL_LOCATION;
    private final static WebServiceException ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_EXCEPTION;
    private final static QName ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/DigitalServices/newibankbvnservice/newibankbvn", "newibankbvn_ptt-bindingQSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
//        	https://ubnsandbox.unionbankng.com:443/bvnnewibankservice/bvnnewibankcheck
            url = new URL("https://unionworkspace.unionbankng.com:443/bvnnewibankservice/bvnnewibankcheck");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_WSDL_LOCATION = url;
        ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_EXCEPTION = e;
    }

    public ONLINECUSTOMERVALIDATIONPttBindingQSService() {
        super(__getWsdlLocation(), ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_QNAME);
    }

    public ONLINECUSTOMERVALIDATIONPttBindingQSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_QNAME, features);
    }

    public ONLINECUSTOMERVALIDATIONPttBindingQSService(URL wsdlLocation) {
        super(wsdlLocation, ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_QNAME);
    }

    public ONLINECUSTOMERVALIDATIONPttBindingQSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_QNAME, features);
    }

    public ONLINECUSTOMERVALIDATIONPttBindingQSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ONLINECUSTOMERVALIDATIONPttBindingQSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ONLINECUSTOMERVALIDATIONPtt
     */
    @WebEndpoint(name = "newibankbvn_ptt-bindingQSPort")
    public ONLINECUSTOMERVALIDATIONPtt getONLINECUSTOMERVALIDATIONPttBindingQSPort() {
        return super.getPort(new QName("http://xmlns.oracle.com/pcbpel/adapter/db/DigitalServices/newibankbvnservice/newibankbvn", "newibankbvn_ptt-bindingQSPort"), ONLINECUSTOMERVALIDATIONPtt.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ONLINECUSTOMERVALIDATIONPtt
     */
    @WebEndpoint(name = "newibankbvn_ptt-bindingQSPort")
    public ONLINECUSTOMERVALIDATIONPtt getONLINECUSTOMERVALIDATIONPttBindingQSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://xmlns.oracle.com/pcbpel/adapter/db/DigitalServices/newibankbvnservice/newibankbvn", "newibankbvn_ptt-bindingQSPort"), ONLINECUSTOMERVALIDATIONPtt.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_EXCEPTION!= null) {
            throw ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_EXCEPTION;
        }
        return ONLINECUSTOMERVALIDATIONPTTBINDINGQSSERVICE_WSDL_LOCATION;
    }

}
