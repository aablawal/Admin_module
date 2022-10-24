package com.unionbankng.future.authorizationserver.ubnonline;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VALIDATE_CUSTOMER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CUSTOMERID" type="{http://xmlns.oracle.com/pcbpel/adapter/db/sp/newibankbvn}RowSet" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "validatecustomer",
    "customerid"
})
@XmlRootElement(name = "OutputParameters")
public class OutputParameters {

    @XmlElementRef(name = "VALIDATE_CUSTOMER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/newibankbvn", type = JAXBElement.class, required = false)
    protected JAXBElement<String> validatecustomer;
    @XmlElementRef(name = "CUSTOMERID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/newibankbvn", type = JAXBElement.class, required = false)
    protected JAXBElement<RowSet> customerid;

    /**
     * Gets the value of the validatecustomer property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVALIDATECUSTOMER() {
        return validatecustomer;
    }

    /**
     * Sets the value of the validatecustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVALIDATECUSTOMER(JAXBElement<String> value) {
        this.validatecustomer = value;
    }

    /**
     * Gets the value of the customerid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RowSet }{@code >}
     *     
     */
    public JAXBElement<RowSet> getCUSTOMERID() {
        return customerid;
    }

    /**
     * Sets the value of the customerid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RowSet }{@code >}
     *     
     */
    public void setCUSTOMERID(JAXBElement<RowSet> value) {
        this.customerid = value;
    }

}
