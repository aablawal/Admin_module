
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getAccountSummaryANDTransactionsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getAccountSummaryANDTransactionsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://service.unionbank.com/}customerAcctsWithTransactionInfoWithBranch" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAccountSummaryANDTransactionsResponse", propOrder = {
    "_return"
})
public class GetAccountSummaryANDTransactionsResponse {

    @XmlElement(name = "return")
    protected CustomerAcctsWithTransactionInfoWithBranch _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerAcctsWithTransactionInfoWithBranch }
     *     
     */
    public CustomerAcctsWithTransactionInfoWithBranch getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerAcctsWithTransactionInfoWithBranch }
     *     
     */
    public void setReturn(CustomerAcctsWithTransactionInfoWithBranch value) {
        this._return = value;
    }

}
