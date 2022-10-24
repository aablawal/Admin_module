package com.unionbankng.future.authorizationserver.ubnonline;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.oracle.xmlns.pcbpel.adapter.db.sp.newibankbvn package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InputParametersBVN_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/newibankbvn", "BVN");
    private final static QName _OutputParametersCUSTOMERID_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/newibankbvn", "CUSTOMERID");
    private final static QName _OutputParametersVALIDATECUSTOMER_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/newibankbvn", "VALIDATE_CUSTOMER");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.oracle.xmlns.pcbpel.adapter.db.sp.newibankbvn
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RowSet }
     * 
     */
    public RowSet createRowSet() {
        return new RowSet();
    }

    /**
     * Create an instance of {@link RowSet.CustList }
     * 
     */
    public RowSet.CustList createRowSetCustList() {
        return new RowSet.CustList();
    }

    /**
     * Create an instance of {@link OutputParameters }
     * 
     */
    public OutputParameters createOutputParameters() {
        return new OutputParameters();
    }

    /**
     * Create an instance of {@link InputParameters }
     * 
     */
    public InputParameters createInputParameters() {
        return new InputParameters();
    }

    /**
     * Create an instance of {@link RowSet.CustList.Custid }
     * 
     */
    public RowSet.CustList.Custid createRowSetCustListCustid() {
        return new RowSet.CustList.Custid();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/newibankbvn", name = "BVN", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersBVN(String value) {
        return new JAXBElement<String>(_InputParametersBVN_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RowSet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/newibankbvn", name = "CUSTOMERID", scope = OutputParameters.class)
    public JAXBElement<RowSet> createOutputParametersCUSTOMERID(RowSet value) {
        return new JAXBElement<RowSet>(_OutputParametersCUSTOMERID_QNAME, RowSet.class, OutputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/newibankbvn", name = "VALIDATE_CUSTOMER", scope = OutputParameters.class)
    public JAXBElement<String> createOutputParametersVALIDATECUSTOMER(String value) {
        return new JAXBElement<String>(_OutputParametersVALIDATECUSTOMER_QNAME, String.class, OutputParameters.class, value);
    }

}
