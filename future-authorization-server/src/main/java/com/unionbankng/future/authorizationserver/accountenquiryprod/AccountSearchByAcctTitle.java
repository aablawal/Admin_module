
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accountSearchByAcctTitle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accountSearchByAcctTitle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cod_prod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currencyDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateopened" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nam_product" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ref_cust_email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accountSearchByAcctTitle", propOrder = {
    "accountname",
    "accountno",
    "codProd",
    "currencyCode",
    "currencyDesc",
    "customerid",
    "dateopened",
    "namProduct",
    "refCustEmail"
})
public class AccountSearchByAcctTitle {

    protected String accountname;
    protected String accountno;
    @XmlElement(name = "cod_prod")
    protected String codProd;
    protected String currencyCode;
    protected String currencyDesc;
    protected String customerid;
    protected String dateopened;
    @XmlElement(name = "nam_product")
    protected String namProduct;
    @XmlElement(name = "ref_cust_email")
    protected String refCustEmail;

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
     * Gets the value of the accountno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountno() {
        return accountno;
    }

    /**
     * Sets the value of the accountno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountno(String value) {
        this.accountno = value;
    }

    /**
     * Gets the value of the codProd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodProd() {
        return codProd;
    }

    /**
     * Sets the value of the codProd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodProd(String value) {
        this.codProd = value;
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
     * Gets the value of the currencyDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyDesc() {
        return currencyDesc;
    }

    /**
     * Sets the value of the currencyDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyDesc(String value) {
        this.currencyDesc = value;
    }

    /**
     * Gets the value of the customerid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * Sets the value of the customerid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerid(String value) {
        this.customerid = value;
    }

    /**
     * Gets the value of the dateopened property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateopened() {
        return dateopened;
    }

    /**
     * Sets the value of the dateopened property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateopened(String value) {
        this.dateopened = value;
    }

    /**
     * Gets the value of the namProduct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamProduct() {
        return namProduct;
    }

    /**
     * Sets the value of the namProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamProduct(String value) {
        this.namProduct = value;
    }

    /**
     * Gets the value of the refCustEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefCustEmail() {
        return refCustEmail;
    }

    /**
     * Sets the value of the refCustEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefCustEmail(String value) {
        this.refCustEmail = value;
    }

}
