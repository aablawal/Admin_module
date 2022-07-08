
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for loanList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="loanList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="brncode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="casa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dat_of_maturity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eff_rate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ln_account_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loan_amt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prod_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prod_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loanList", propOrder = {
    "balance",
    "brncode",
    "casa",
    "datOfMaturity",
    "effRate",
    "lnAccountNo",
    "loanAmt",
    "name",
    "prodCode",
    "prodName"
})
public class LoanList {

    protected String balance;
    protected String brncode;
    protected String casa;
    @XmlElement(name = "dat_of_maturity")
    protected String datOfMaturity;
    @XmlElement(name = "eff_rate")
    protected String effRate;
    @XmlElement(name = "ln_account_no")
    protected String lnAccountNo;
    @XmlElement(name = "loan_amt")
    protected String loanAmt;
    protected String name;
    @XmlElement(name = "prod_code")
    protected String prodCode;
    @XmlElement(name = "prod_name")
    protected String prodName;

    /**
     * Gets the value of the balance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalance(String value) {
        this.balance = value;
    }

    /**
     * Gets the value of the brncode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrncode() {
        return brncode;
    }

    /**
     * Sets the value of the brncode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrncode(String value) {
        this.brncode = value;
    }

    /**
     * Gets the value of the casa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCasa() {
        return casa;
    }

    /**
     * Sets the value of the casa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCasa(String value) {
        this.casa = value;
    }

    /**
     * Gets the value of the datOfMaturity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatOfMaturity() {
        return datOfMaturity;
    }

    /**
     * Sets the value of the datOfMaturity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatOfMaturity(String value) {
        this.datOfMaturity = value;
    }

    /**
     * Gets the value of the effRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffRate() {
        return effRate;
    }

    /**
     * Sets the value of the effRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffRate(String value) {
        this.effRate = value;
    }

    /**
     * Gets the value of the lnAccountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLnAccountNo() {
        return lnAccountNo;
    }

    /**
     * Sets the value of the lnAccountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLnAccountNo(String value) {
        this.lnAccountNo = value;
    }

    /**
     * Gets the value of the loanAmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanAmt() {
        return loanAmt;
    }

    /**
     * Sets the value of the loanAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanAmt(String value) {
        this.loanAmt = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the prodCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdCode() {
        return prodCode;
    }

    /**
     * Sets the value of the prodCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdCode(String value) {
        this.prodCode = value;
    }

    /**
     * Gets the value of the prodName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * Sets the value of the prodName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdName(String value) {
        this.prodName = value;
    }

}
