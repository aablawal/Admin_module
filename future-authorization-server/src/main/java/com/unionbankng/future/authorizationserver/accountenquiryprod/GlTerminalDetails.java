
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for glTerminalDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="glTerminalDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cashGL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgBRN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="termAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="terminalID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "glTerminalDetails", propOrder = {
    "cashGL",
    "channel",
    "orgBRN",
    "termAddr",
    "terminalID"
})
public class GlTerminalDetails {

    protected String cashGL;
    protected String channel;
    protected String orgBRN;
    protected String termAddr;
    protected String terminalID;

    /**
     * Gets the value of the cashGL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCashGL() {
        return cashGL;
    }

    /**
     * Sets the value of the cashGL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCashGL(String value) {
        this.cashGL = value;
    }

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

    /**
     * Gets the value of the orgBRN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgBRN() {
        return orgBRN;
    }

    /**
     * Sets the value of the orgBRN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgBRN(String value) {
        this.orgBRN = value;
    }

    /**
     * Gets the value of the termAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermAddr() {
        return termAddr;
    }

    /**
     * Sets the value of the termAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermAddr(String value) {
        this.termAddr = value;
    }

    /**
     * Gets the value of the terminalID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminalID() {
        return terminalID;
    }

    /**
     * Sets the value of the terminalID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminalID(String value) {
        this.terminalID = value;
    }

}
