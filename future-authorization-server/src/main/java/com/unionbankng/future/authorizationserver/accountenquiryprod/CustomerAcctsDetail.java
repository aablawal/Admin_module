
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for customerAcctsDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="customerAcctsDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aTMStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountOfficer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amountCreditMTD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amountCreditYTD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amountDebitMTD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amountDebitYTD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amountHold" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amountLastCredit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amountLastDebit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="availableBalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bookBalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="branchCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="branchName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="custID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dAUEEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dAUELimit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dAUEStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateOpened" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interestPaidYTD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interestReceivedYTD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastCreditDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastCreditInterestAccrued" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastDebitDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastDebitInterestAccrued" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastMaintainedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastSerialofCheque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastUsedChequeNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maintenanceAuthorizedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oDLimit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="profitCenter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceChargeYTD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="staff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strAdd1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strAdd2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strAdd3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strZip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unavailableBalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customerAcctsDetail", propOrder = {
    "atmStatus",
    "accountName",
    "accountOfficer",
    "accountStatus",
    "amountCreditMTD",
    "amountCreditYTD",
    "amountDebitMTD",
    "amountDebitYTD",
    "amountHold",
    "amountLastCredit",
    "amountLastDebit",
    "availableBalance",
    "bookBalance",
    "branchCode",
    "branchName",
    "currency",
    "currencyCode",
    "custID",
    "customerName",
    "daueEndDate",
    "daueLimit",
    "daueStartDate",
    "dateOpened",
    "description",
    "interestPaidYTD",
    "interestReceivedYTD",
    "lastCreditDate",
    "lastCreditInterestAccrued",
    "lastDebitDate",
    "lastDebitInterestAccrued",
    "lastMaintainedBy",
    "lastSerialofCheque",
    "lastUsedChequeNo",
    "maintenanceAuthorizedBy",
    "odLimit",
    "phone",
    "productCode",
    "productName",
    "profitCenter",
    "serviceChargeYTD",
    "staff",
    "strAdd1",
    "strAdd2",
    "strAdd3",
    "strCity",
    "strCountry",
    "strState",
    "strZip",
    "unavailableBalance"
})
public class CustomerAcctsDetail {

    @XmlElement(name = "aTMStatus")
    protected String atmStatus;
    protected String accountName;
    protected String accountOfficer;
    protected String accountStatus;
    protected String amountCreditMTD;
    protected String amountCreditYTD;
    protected String amountDebitMTD;
    protected String amountDebitYTD;
    protected String amountHold;
    protected String amountLastCredit;
    protected String amountLastDebit;
    protected String availableBalance;
    protected String bookBalance;
    protected String branchCode;
    protected String branchName;
    protected String currency;
    protected String currencyCode;
    protected String custID;
    protected String customerName;
    @XmlElement(name = "dAUEEndDate")
    protected String daueEndDate;
    @XmlElement(name = "dAUELimit")
    protected String daueLimit;
    @XmlElement(name = "dAUEStartDate")
    protected String daueStartDate;
    protected String dateOpened;
    protected String description;
    protected String interestPaidYTD;
    protected String interestReceivedYTD;
    protected String lastCreditDate;
    protected String lastCreditInterestAccrued;
    protected String lastDebitDate;
    protected String lastDebitInterestAccrued;
    protected String lastMaintainedBy;
    protected String lastSerialofCheque;
    protected String lastUsedChequeNo;
    protected String maintenanceAuthorizedBy;
    @XmlElement(name = "oDLimit")
    protected String odLimit;
    protected String phone;
    protected String productCode;
    protected String productName;
    protected String profitCenter;
    protected String serviceChargeYTD;
    protected String staff;
    protected String strAdd1;
    protected String strAdd2;
    protected String strAdd3;
    protected String strCity;
    protected String strCountry;
    protected String strState;
    protected String strZip;
    protected String unavailableBalance;

    /**
     * Gets the value of the atmStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATMStatus() {
        return atmStatus;
    }

    /**
     * Sets the value of the atmStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATMStatus(String value) {
        this.atmStatus = value;
    }

    /**
     * Gets the value of the accountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets the value of the accountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountName(String value) {
        this.accountName = value;
    }

    /**
     * Gets the value of the accountOfficer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountOfficer() {
        return accountOfficer;
    }

    /**
     * Sets the value of the accountOfficer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountOfficer(String value) {
        this.accountOfficer = value;
    }

    /**
     * Gets the value of the accountStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountStatus() {
        return accountStatus;
    }

    /**
     * Sets the value of the accountStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountStatus(String value) {
        this.accountStatus = value;
    }

    /**
     * Gets the value of the amountCreditMTD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountCreditMTD() {
        return amountCreditMTD;
    }

    /**
     * Sets the value of the amountCreditMTD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountCreditMTD(String value) {
        this.amountCreditMTD = value;
    }

    /**
     * Gets the value of the amountCreditYTD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountCreditYTD() {
        return amountCreditYTD;
    }

    /**
     * Sets the value of the amountCreditYTD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountCreditYTD(String value) {
        this.amountCreditYTD = value;
    }

    /**
     * Gets the value of the amountDebitMTD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountDebitMTD() {
        return amountDebitMTD;
    }

    /**
     * Sets the value of the amountDebitMTD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountDebitMTD(String value) {
        this.amountDebitMTD = value;
    }

    /**
     * Gets the value of the amountDebitYTD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountDebitYTD() {
        return amountDebitYTD;
    }

    /**
     * Sets the value of the amountDebitYTD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountDebitYTD(String value) {
        this.amountDebitYTD = value;
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
     * Gets the value of the amountLastCredit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountLastCredit() {
        return amountLastCredit;
    }

    /**
     * Sets the value of the amountLastCredit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountLastCredit(String value) {
        this.amountLastCredit = value;
    }

    /**
     * Gets the value of the amountLastDebit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountLastDebit() {
        return amountLastDebit;
    }

    /**
     * Sets the value of the amountLastDebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountLastDebit(String value) {
        this.amountLastDebit = value;
    }

    /**
     * Gets the value of the availableBalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvailableBalance() {
        return availableBalance;
    }

    /**
     * Sets the value of the availableBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvailableBalance(String value) {
        this.availableBalance = value;
    }

    /**
     * Gets the value of the bookBalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookBalance() {
        return bookBalance;
    }

    /**
     * Sets the value of the bookBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookBalance(String value) {
        this.bookBalance = value;
    }

    /**
     * Gets the value of the branchCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * Sets the value of the branchCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchCode(String value) {
        this.branchCode = value;
    }

    /**
     * Gets the value of the branchName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * Sets the value of the branchName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchName(String value) {
        this.branchName = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the custID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustID() {
        return custID;
    }

    /**
     * Sets the value of the custID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustID(String value) {
        this.custID = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the daueEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDAUEEndDate() {
        return daueEndDate;
    }

    /**
     * Sets the value of the daueEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDAUEEndDate(String value) {
        this.daueEndDate = value;
    }

    /**
     * Gets the value of the daueLimit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDAUELimit() {
        return daueLimit;
    }

    /**
     * Sets the value of the daueLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDAUELimit(String value) {
        this.daueLimit = value;
    }

    /**
     * Gets the value of the daueStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDAUEStartDate() {
        return daueStartDate;
    }

    /**
     * Sets the value of the daueStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDAUEStartDate(String value) {
        this.daueStartDate = value;
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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the interestPaidYTD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterestPaidYTD() {
        return interestPaidYTD;
    }

    /**
     * Sets the value of the interestPaidYTD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterestPaidYTD(String value) {
        this.interestPaidYTD = value;
    }

    /**
     * Gets the value of the interestReceivedYTD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterestReceivedYTD() {
        return interestReceivedYTD;
    }

    /**
     * Sets the value of the interestReceivedYTD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterestReceivedYTD(String value) {
        this.interestReceivedYTD = value;
    }

    /**
     * Gets the value of the lastCreditDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastCreditDate() {
        return lastCreditDate;
    }

    /**
     * Sets the value of the lastCreditDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastCreditDate(String value) {
        this.lastCreditDate = value;
    }

    /**
     * Gets the value of the lastCreditInterestAccrued property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastCreditInterestAccrued() {
        return lastCreditInterestAccrued;
    }

    /**
     * Sets the value of the lastCreditInterestAccrued property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastCreditInterestAccrued(String value) {
        this.lastCreditInterestAccrued = value;
    }

    /**
     * Gets the value of the lastDebitDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastDebitDate() {
        return lastDebitDate;
    }

    /**
     * Sets the value of the lastDebitDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastDebitDate(String value) {
        this.lastDebitDate = value;
    }

    /**
     * Gets the value of the lastDebitInterestAccrued property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastDebitInterestAccrued() {
        return lastDebitInterestAccrued;
    }

    /**
     * Sets the value of the lastDebitInterestAccrued property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastDebitInterestAccrued(String value) {
        this.lastDebitInterestAccrued = value;
    }

    /**
     * Gets the value of the lastMaintainedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastMaintainedBy() {
        return lastMaintainedBy;
    }

    /**
     * Sets the value of the lastMaintainedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastMaintainedBy(String value) {
        this.lastMaintainedBy = value;
    }

    /**
     * Gets the value of the lastSerialofCheque property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastSerialofCheque() {
        return lastSerialofCheque;
    }

    /**
     * Sets the value of the lastSerialofCheque property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastSerialofCheque(String value) {
        this.lastSerialofCheque = value;
    }

    /**
     * Gets the value of the lastUsedChequeNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUsedChequeNo() {
        return lastUsedChequeNo;
    }

    /**
     * Sets the value of the lastUsedChequeNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUsedChequeNo(String value) {
        this.lastUsedChequeNo = value;
    }

    /**
     * Gets the value of the maintenanceAuthorizedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaintenanceAuthorizedBy() {
        return maintenanceAuthorizedBy;
    }

    /**
     * Sets the value of the maintenanceAuthorizedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaintenanceAuthorizedBy(String value) {
        this.maintenanceAuthorizedBy = value;
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

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the productCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets the value of the productCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCode(String value) {
        this.productCode = value;
    }

    /**
     * Gets the value of the productName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the value of the productName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    /**
     * Gets the value of the profitCenter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfitCenter() {
        return profitCenter;
    }

    /**
     * Sets the value of the profitCenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfitCenter(String value) {
        this.profitCenter = value;
    }

    /**
     * Gets the value of the serviceChargeYTD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceChargeYTD() {
        return serviceChargeYTD;
    }

    /**
     * Sets the value of the serviceChargeYTD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceChargeYTD(String value) {
        this.serviceChargeYTD = value;
    }

    /**
     * Gets the value of the staff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaff() {
        return staff;
    }

    /**
     * Sets the value of the staff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaff(String value) {
        this.staff = value;
    }

    /**
     * Gets the value of the strAdd1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrAdd1() {
        return strAdd1;
    }

    /**
     * Sets the value of the strAdd1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrAdd1(String value) {
        this.strAdd1 = value;
    }

    /**
     * Gets the value of the strAdd2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrAdd2() {
        return strAdd2;
    }

    /**
     * Sets the value of the strAdd2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrAdd2(String value) {
        this.strAdd2 = value;
    }

    /**
     * Gets the value of the strAdd3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrAdd3() {
        return strAdd3;
    }

    /**
     * Sets the value of the strAdd3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrAdd3(String value) {
        this.strAdd3 = value;
    }

    /**
     * Gets the value of the strCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrCity() {
        return strCity;
    }

    /**
     * Sets the value of the strCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrCity(String value) {
        this.strCity = value;
    }

    /**
     * Gets the value of the strCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrCountry() {
        return strCountry;
    }

    /**
     * Sets the value of the strCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrCountry(String value) {
        this.strCountry = value;
    }

    /**
     * Gets the value of the strState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrState() {
        return strState;
    }

    /**
     * Sets the value of the strState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrState(String value) {
        this.strState = value;
    }

    /**
     * Gets the value of the strZip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrZip() {
        return strZip;
    }

    /**
     * Sets the value of the strZip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrZip(String value) {
        this.strZip = value;
    }

    /**
     * Gets the value of the unavailableBalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnavailableBalance() {
        return unavailableBalance;
    }

    /**
     * Sets the value of the unavailableBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnavailableBalance(String value) {
        this.unavailableBalance = value;
    }

}
