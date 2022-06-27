
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for overdraftList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="overdraftList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amt_drawing_power" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amt_limit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bal_available" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cod_acct_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cod_acct_title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cod_limit_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dat_limit_end" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dat_limit_start" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rat_int" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rat_int_effective" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rat_var_od" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "overdraftList", propOrder = {
    "amtDrawingPower",
    "amtLimit",
    "balAvailable",
    "codAcctNo",
    "codAcctTitle",
    "codLimitNo",
    "datLimitEnd",
    "datLimitStart",
    "ratInt",
    "ratIntEffective",
    "ratVarOd"
})
public class OverdraftList {

    @XmlElement(name = "amt_drawing_power")
    protected String amtDrawingPower;
    @XmlElement(name = "amt_limit")
    protected String amtLimit;
    @XmlElement(name = "bal_available")
    protected String balAvailable;
    @XmlElement(name = "cod_acct_no")
    protected String codAcctNo;
    @XmlElement(name = "cod_acct_title")
    protected String codAcctTitle;
    @XmlElement(name = "cod_limit_no")
    protected String codLimitNo;
    @XmlElement(name = "dat_limit_end")
    protected String datLimitEnd;
    @XmlElement(name = "dat_limit_start")
    protected String datLimitStart;
    @XmlElement(name = "rat_int")
    protected String ratInt;
    @XmlElement(name = "rat_int_effective")
    protected String ratIntEffective;
    @XmlElement(name = "rat_var_od")
    protected String ratVarOd;

    /**
     * Gets the value of the amtDrawingPower property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmtDrawingPower() {
        return amtDrawingPower;
    }

    /**
     * Sets the value of the amtDrawingPower property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmtDrawingPower(String value) {
        this.amtDrawingPower = value;
    }

    /**
     * Gets the value of the amtLimit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmtLimit() {
        return amtLimit;
    }

    /**
     * Sets the value of the amtLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmtLimit(String value) {
        this.amtLimit = value;
    }

    /**
     * Gets the value of the balAvailable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalAvailable() {
        return balAvailable;
    }

    /**
     * Sets the value of the balAvailable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalAvailable(String value) {
        this.balAvailable = value;
    }

    /**
     * Gets the value of the codAcctNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAcctNo() {
        return codAcctNo;
    }

    /**
     * Sets the value of the codAcctNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAcctNo(String value) {
        this.codAcctNo = value;
    }

    /**
     * Gets the value of the codAcctTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAcctTitle() {
        return codAcctTitle;
    }

    /**
     * Sets the value of the codAcctTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAcctTitle(String value) {
        this.codAcctTitle = value;
    }

    /**
     * Gets the value of the codLimitNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodLimitNo() {
        return codLimitNo;
    }

    /**
     * Sets the value of the codLimitNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodLimitNo(String value) {
        this.codLimitNo = value;
    }

    /**
     * Gets the value of the datLimitEnd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatLimitEnd() {
        return datLimitEnd;
    }

    /**
     * Sets the value of the datLimitEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatLimitEnd(String value) {
        this.datLimitEnd = value;
    }

    /**
     * Gets the value of the datLimitStart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatLimitStart() {
        return datLimitStart;
    }

    /**
     * Sets the value of the datLimitStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatLimitStart(String value) {
        this.datLimitStart = value;
    }

    /**
     * Gets the value of the ratInt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatInt() {
        return ratInt;
    }

    /**
     * Sets the value of the ratInt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatInt(String value) {
        this.ratInt = value;
    }

    /**
     * Gets the value of the ratIntEffective property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatIntEffective() {
        return ratIntEffective;
    }

    /**
     * Sets the value of the ratIntEffective property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatIntEffective(String value) {
        this.ratIntEffective = value;
    }

    /**
     * Gets the value of the ratVarOd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatVarOd() {
        return ratVarOd;
    }

    /**
     * Sets the value of the ratVarOd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatVarOd(String value) {
        this.ratVarOd = value;
    }

}
