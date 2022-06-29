
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for auditInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="auditInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="createdBY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateCreated" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="event" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serverIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="staffID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tranref" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "auditInformation", propOrder = {
    "createdBY",
    "dateCreated",
    "event",
    "serverIP",
    "staffID",
    "tranref"
})
public class AuditInformation {

    protected String createdBY;
    protected String dateCreated;
    protected String event;
    protected String serverIP;
    protected String staffID;
    protected String tranref;

    /**
     * Gets the value of the createdBY property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedBY() {
        return createdBY;
    }

    /**
     * Sets the value of the createdBY property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedBY(String value) {
        this.createdBY = value;
    }

    /**
     * Gets the value of the dateCreated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateCreated(String value) {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvent() {
        return event;
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvent(String value) {
        this.event = value;
    }

    /**
     * Gets the value of the serverIP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerIP() {
        return serverIP;
    }

    /**
     * Sets the value of the serverIP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerIP(String value) {
        this.serverIP = value;
    }

    /**
     * Gets the value of the staffID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaffID() {
        return staffID;
    }

    /**
     * Sets the value of the staffID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaffID(String value) {
        this.staffID = value;
    }

    /**
     * Gets the value of the tranref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranref() {
        return tranref;
    }

    /**
     * Sets the value of the tranref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranref(String value) {
        this.tranref = value;
    }

}
