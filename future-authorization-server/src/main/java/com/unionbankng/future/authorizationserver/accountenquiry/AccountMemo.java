
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accountMemo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accountMemo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="memoInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cod_last_mnt_chkrid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cod_last_mnt_makerid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="custid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datlastmnt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="severity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accountMemo", propOrder = {
    "memoInfo",
    "codLastMntChkrid",
    "codLastMntMakerid",
    "custid",
    "datlastmnt",
    "severity"
})
public class AccountMemo {

    protected String memoInfo;
    @XmlElement(name = "cod_last_mnt_chkrid")
    protected String codLastMntChkrid;
    @XmlElement(name = "cod_last_mnt_makerid")
    protected String codLastMntMakerid;
    protected String custid;
    protected String datlastmnt;
    protected String severity;

    /**
     * Gets the value of the memoInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemoInfo() {
        return memoInfo;
    }

    /**
     * Sets the value of the memoInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemoInfo(String value) {
        this.memoInfo = value;
    }

    /**
     * Gets the value of the codLastMntChkrid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodLastMntChkrid() {
        return codLastMntChkrid;
    }

    /**
     * Sets the value of the codLastMntChkrid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodLastMntChkrid(String value) {
        this.codLastMntChkrid = value;
    }

    /**
     * Gets the value of the codLastMntMakerid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodLastMntMakerid() {
        return codLastMntMakerid;
    }

    /**
     * Sets the value of the codLastMntMakerid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodLastMntMakerid(String value) {
        this.codLastMntMakerid = value;
    }

    /**
     * Gets the value of the custid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustid() {
        return custid;
    }

    /**
     * Sets the value of the custid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustid(String value) {
        this.custid = value;
    }

    /**
     * Gets the value of the datlastmnt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatlastmnt() {
        return datlastmnt;
    }

    /**
     * Sets the value of the datlastmnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatlastmnt(String value) {
        this.datlastmnt = value;
    }

    /**
     * Gets the value of the severity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * Sets the value of the severity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeverity(String value) {
        this.severity = value;
    }

}
