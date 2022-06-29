
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for managersChequeDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="managersChequeDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beneficiary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="chequeNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sortCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tranCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "managersChequeDetails", propOrder = {
    "accountNo",
    "amount",
    "beneficiary",
    "chequeNo",
    "sortCode",
    "tranCode"
})
public class ManagersChequeDetails {

    protected String accountNo;
    protected String amount;
    protected String beneficiary;
    protected String chequeNo;
    protected String sortCode;
    protected String tranCode;

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
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmount(String value) {
        this.amount = value;
    }

    /**
     * Gets the value of the beneficiary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneficiary() {
        return beneficiary;
    }

    /**
     * Sets the value of the beneficiary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneficiary(String value) {
        this.beneficiary = value;
    }

    /**
     * Gets the value of the chequeNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChequeNo() {
        return chequeNo;
    }

    /**
     * Sets the value of the chequeNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChequeNo(String value) {
        this.chequeNo = value;
    }

    /**
     * Gets the value of the sortCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortCode() {
        return sortCode;
    }

    /**
     * Sets the value of the sortCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortCode(String value) {
        this.sortCode = value;
    }

    /**
     * Gets the value of the tranCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranCode() {
        return tranCode;
    }

    /**
     * Sets the value of the tranCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranCode(String value) {
        this.tranCode = value;
    }

}
