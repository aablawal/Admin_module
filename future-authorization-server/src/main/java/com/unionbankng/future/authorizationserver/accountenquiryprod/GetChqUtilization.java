
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getChqUtilization complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getChqUtilization">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="account_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="instrument_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="control_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getChqUtilization", propOrder = {
    "accountNo",
    "instrumentNo",
    "controlNo"
})
public class GetChqUtilization {

    @XmlElement(name = "account_no")
    protected String accountNo;
    @XmlElement(name = "instrument_no")
    protected String instrumentNo;
    @XmlElement(name = "control_no")
    protected String controlNo;

    /**
     * Gets the value of the accountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * Sets the value of the accountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNo(String value) {
        this.accountNo = value;
    }

    /**
     * Gets the value of the instrumentNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstrumentNo() {
        return instrumentNo;
    }

    /**
     * Sets the value of the instrumentNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstrumentNo(String value) {
        this.instrumentNo = value;
    }

    /**
     * Gets the value of the controlNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControlNo() {
        return controlNo;
    }

    /**
     * Sets the value of the controlNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControlNo(String value) {
        this.controlNo = value;
    }

}
