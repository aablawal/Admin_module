
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getAccountSummaryANDTxnGenericResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getAccountSummaryANDTxnGenericResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://service.unionbank.com/}customerAcctsWithTranInfoWithBranchGeneric" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAccountSummaryANDTxnGenericResponse", propOrder = {
    "_return"
})
public class GetAccountSummaryANDTxnGenericResponse {

    @XmlElement(name = "return")
    protected CustomerAcctsWithTranInfoWithBranchGeneric _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerAcctsWithTranInfoWithBranchGeneric }
     *     
     */
    public CustomerAcctsWithTranInfoWithBranchGeneric getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerAcctsWithTranInfoWithBranchGeneric }
     *     
     */
    public void setReturn(CustomerAcctsWithTranInfoWithBranchGeneric value) {
        this._return = value;
    }

}
