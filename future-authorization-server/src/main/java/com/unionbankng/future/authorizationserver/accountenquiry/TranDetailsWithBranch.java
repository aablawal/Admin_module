
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tranDetailsWithBranch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tranDetailsWithBranch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lodgement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="withdraw" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xrownumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xybalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="branchid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="branchname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tranDetailsWithBranch", propOrder = {
    "desc",
    "lodgement",
    "refNo",
    "valueDate",
    "withdraw",
    "xrownumber",
    "xybalance",
    "branchid",
    "branchname",
    "tDate"
})
public class TranDetailsWithBranch {

    protected String desc;
    protected String lodgement;
    protected String refNo;
    protected String valueDate;
    protected String withdraw;
    protected String xrownumber;
    protected String xybalance;
    protected String branchid;
    protected String branchname;
    protected String tDate;

    /**
     * Gets the value of the desc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesc(String value) {
        this.desc = value;
    }

    /**
     * Gets the value of the lodgement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLodgement() {
        return lodgement;
    }

    /**
     * Sets the value of the lodgement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLodgement(String value) {
        this.lodgement = value;
    }

    /**
     * Gets the value of the refNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefNo() {
        return refNo;
    }

    /**
     * Sets the value of the refNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefNo(String value) {
        this.refNo = value;
    }

    /**
     * Gets the value of the valueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueDate() {
        return valueDate;
    }

    /**
     * Sets the value of the valueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueDate(String value) {
        this.valueDate = value;
    }

    /**
     * Gets the value of the withdraw property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWithdraw() {
        return withdraw;
    }

    /**
     * Sets the value of the withdraw property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWithdraw(String value) {
        this.withdraw = value;
    }

    /**
     * Gets the value of the xrownumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXrownumber() {
        return xrownumber;
    }

    /**
     * Sets the value of the xrownumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXrownumber(String value) {
        this.xrownumber = value;
    }

    /**
     * Gets the value of the xybalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXybalance() {
        return xybalance;
    }

    /**
     * Sets the value of the xybalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXybalance(String value) {
        this.xybalance = value;
    }

    /**
     * Gets the value of the branchid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchid() {
        return branchid;
    }

    /**
     * Sets the value of the branchid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchid(String value) {
        this.branchid = value;
    }

    /**
     * Gets the value of the branchname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchname() {
        return branchname;
    }

    /**
     * Sets the value of the branchname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchname(String value) {
        this.branchname = value;
    }

    /**
     * Gets the value of the tDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTDate() {
        return tDate;
    }

    /**
     * Sets the value of the tDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTDate(String value) {
        this.tDate = value;
    }

}
