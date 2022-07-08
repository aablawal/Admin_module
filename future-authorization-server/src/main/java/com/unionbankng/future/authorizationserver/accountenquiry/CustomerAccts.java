
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for customerAccts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="customerAccts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountcurrency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountproduct" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountstatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amountHold" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="availabletowithdraw" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clearedBalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="closingBalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateOpened" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hasCOT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hasIMAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lodgements" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="netbalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noofLodgements" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noofWithdrawals" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="openingBalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalbalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unclearedBalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="withdrawals" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dAUE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flgempacct" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flgstaff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oDLimit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customerAccts", propOrder = {
    "accountNo",
    "accountcurrency",
    "accountname",
    "accountproduct",
    "accountstatus",
    "amountHold",
    "availabletowithdraw",
    "clearedBalance",
    "closingBalance",
    "dateOpened",
    "hasCOT",
    "hasIMAGE",
    "lodgements",
    "netbalance",
    "noofLodgements",
    "noofWithdrawals",
    "openingBalance",
    "totalbalance",
    "unclearedBalance",
    "withdrawals",
    "daue",
    "flgempacct",
    "flgstaff",
    "odLimit"
})
public class CustomerAccts {

    protected String accountNo;
    protected String accountcurrency;
    protected String accountname;
    protected String accountproduct;
    protected String accountstatus;
    protected String amountHold;
    protected String availabletowithdraw;
    protected String clearedBalance;
    protected String closingBalance;
    protected String dateOpened;
    protected String hasCOT;
    protected String hasIMAGE;
    protected String lodgements;
    protected String netbalance;
    protected String noofLodgements;
    protected String noofWithdrawals;
    protected String openingBalance;
    protected String totalbalance;
    protected String unclearedBalance;
    protected String withdrawals;
    @XmlElement(name = "dAUE")
    protected String daue;
    protected String flgempacct;
    protected String flgstaff;
    @XmlElement(name = "oDLimit")
    protected String odLimit;

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
     * Gets the value of the accountcurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountcurrency() {
        return accountcurrency;
    }

    /**
     * Sets the value of the accountcurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountcurrency(String value) {
        this.accountcurrency = value;
    }

    /**
     * Gets the value of the accountname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountname() {
        return accountname;
    }

    /**
     * Sets the value of the accountname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountname(String value) {
        this.accountname = value;
    }

    /**
     * Gets the value of the accountproduct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountproduct() {
        return accountproduct;
    }

    /**
     * Sets the value of the accountproduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountproduct(String value) {
        this.accountproduct = value;
    }

    /**
     * Gets the value of the accountstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountstatus() {
        return accountstatus;
    }

    /**
     * Sets the value of the accountstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountstatus(String value) {
        this.accountstatus = value;
    }

    /**
     * Gets the value of the amountHold property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountHold() {
        return amountHold;
    }

    /**
     * Sets the value of the amountHold property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountHold(String value) {
        this.amountHold = value;
    }

    /**
     * Gets the value of the availabletowithdraw property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvailabletowithdraw() {
        return availabletowithdraw;
    }

    /**
     * Sets the value of the availabletowithdraw property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvailabletowithdraw(String value) {
        this.availabletowithdraw = value;
    }

    /**
     * Gets the value of the clearedBalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClearedBalance() {
        return clearedBalance;
    }

    /**
     * Sets the value of the clearedBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClearedBalance(String value) {
        this.clearedBalance = value;
    }

    /**
     * Gets the value of the closingBalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClosingBalance() {
        return closingBalance;
    }

    /**
     * Sets the value of the closingBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClosingBalance(String value) {
        this.closingBalance = value;
    }

    /**
     * Gets the value of the dateOpened property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOpened() {
        return dateOpened;
    }

    /**
     * Sets the value of the dateOpened property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOpened(String value) {
        this.dateOpened = value;
    }

    /**
     * Gets the value of the hasCOT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasCOT() {
        return hasCOT;
    }

    /**
     * Sets the value of the hasCOT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasCOT(String value) {
        this.hasCOT = value;
    }

    /**
     * Gets the value of the hasIMAGE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasIMAGE() {
        return hasIMAGE;
    }

    /**
     * Sets the value of the hasIMAGE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasIMAGE(String value) {
        this.hasIMAGE = value;
    }

    /**
     * Gets the value of the lodgements property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLodgements() {
        return lodgements;
    }

    /**
     * Sets the value of the lodgements property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLodgements(String value) {
        this.lodgements = value;
    }

    /**
     * Gets the value of the netbalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetbalance() {
        return netbalance;
    }

    /**
     * Sets the value of the netbalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetbalance(String value) {
        this.netbalance = value;
    }

    /**
     * Gets the value of the noofLodgements property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoofLodgements() {
        return noofLodgements;
    }

    /**
     * Sets the value of the noofLodgements property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoofLodgements(String value) {
        this.noofLodgements = value;
    }

    /**
     * Gets the value of the noofWithdrawals property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoofWithdrawals() {
        return noofWithdrawals;
    }

    /**
     * Sets the value of the noofWithdrawals property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoofWithdrawals(String value) {
        this.noofWithdrawals = value;
    }

    /**
     * Gets the value of the openingBalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpeningBalance() {
        return openingBalance;
    }

    /**
     * Sets the value of the openingBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpeningBalance(String value) {
        this.openingBalance = value;
    }

    /**
     * Gets the value of the totalbalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalbalance() {
        return totalbalance;
    }

    /**
     * Sets the value of the totalbalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalbalance(String value) {
        this.totalbalance = value;
    }

    /**
     * Gets the value of the unclearedBalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnclearedBalance() {
        return unclearedBalance;
    }

    /**
     * Sets the value of the unclearedBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnclearedBalance(String value) {
        this.unclearedBalance = value;
    }

    /**
     * Gets the value of the withdrawals property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWithdrawals() {
        return withdrawals;
    }

    /**
     * Sets the value of the withdrawals property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWithdrawals(String value) {
        this.withdrawals = value;
    }

    /**
     * Gets the value of the daue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDAUE() {
        return daue;
    }

    /**
     * Sets the value of the daue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDAUE(String value) {
        this.daue = value;
    }

    /**
     * Gets the value of the flgempacct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgempacct() {
        return flgempacct;
    }

    /**
     * Sets the value of the flgempacct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgempacct(String value) {
        this.flgempacct = value;
    }

    /**
     * Gets the value of the flgstaff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgstaff() {
        return flgstaff;
    }

    /**
     * Sets the value of the flgstaff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgstaff(String value) {
        this.flgstaff = value;
    }

    /**
     * Gets the value of the odLimit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getODLimit() {
        return odLimit;
    }

    /**
     * Sets the value of the odLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setODLimit(String value) {
        this.odLimit = value;
    }

}
