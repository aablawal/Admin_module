
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getChequeNumberInquiry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getChequeNumberInquiry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="strAccountNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strChequeNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getChequeNumberInquiry", propOrder = {
    "strAccountNo",
    "strChequeNo"
})
public class GetChequeNumberInquiry {

    protected String strAccountNo;
    protected String strChequeNo;

    /**
     * Gets the value of the strAccountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrAccountNo() {
        return strAccountNo;
    }

    /**
     * Sets the value of the strAccountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrAccountNo(String value) {
        this.strAccountNo = value;
    }

    /**
     * Gets the value of the strChequeNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrChequeNo() {
        return strChequeNo;
    }

    /**
     * Sets the value of the strChequeNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrChequeNo(String value) {
        this.strChequeNo = value;
    }

}
