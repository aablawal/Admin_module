
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for UBNHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UBNHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MessageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CorrelationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InternalMessageId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClientId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Service" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Operation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClientIp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestedTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ResponseTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="BusinessKeyPairs" type="{http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader}BusinessKeyPairs" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UBNHeaderType", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader", propOrder = {
    "messageId",
    "correlationId",
    "internalMessageId",
    "clientId",
    "service",
    "operation",
    "clientIp",
    "userId",
    "requestedTimestamp",
    "responseTimestamp",
    "businessKeyPairs"
})
public class UBNHeaderType {

    @XmlElement(name = "MessageId", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader", required = true)
    protected String messageId;
    @XmlElement(name = "CorrelationId", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader", required = true)
    protected String correlationId;
    @XmlElement(name = "InternalMessageId", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader")
    protected String internalMessageId;
    @XmlElement(name = "ClientId", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader")
    protected String clientId;
    @XmlElement(name = "Service", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader")
    protected String service;
    @XmlElement(name = "Operation", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader")
    protected String operation;
    @XmlElement(name = "ClientIp", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader", required = true)
    protected String clientIp;
    @XmlElement(name = "UserId", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader")
    protected String userId;
    @XmlElement(name = "RequestedTimestamp", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestedTimestamp;
    @XmlElement(name = "ResponseTimestamp", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar responseTimestamp;
    @XmlElement(name = "BusinessKeyPairs", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader")
    protected BusinessKeyPairs businessKeyPairs;

    /**
     * Gets the value of the messageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Sets the value of the messageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageId(String value) {
        this.messageId = value;
    }

    /**
     * Gets the value of the correlationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Sets the value of the correlationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrelationId(String value) {
        this.correlationId = value;
    }

    /**
     * Gets the value of the internalMessageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternalMessageId() {
        return internalMessageId;
    }

    /**
     * Sets the value of the internalMessageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternalMessageId(String value) {
        this.internalMessageId = value;
    }

    /**
     * Gets the value of the clientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the value of the clientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientId(String value) {
        this.clientId = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setService(String value) {
        this.service = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

    /**
     * Gets the value of the clientIp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * Sets the value of the clientIp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientIp(String value) {
        this.clientIp = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the requestedTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestedTimestamp() {
        return requestedTimestamp;
    }

    /**
     * Sets the value of the requestedTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestedTimestamp(XMLGregorianCalendar value) {
        this.requestedTimestamp = value;
    }

    /**
     * Gets the value of the responseTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getResponseTimestamp() {
        return responseTimestamp;
    }

    /**
     * Sets the value of the responseTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setResponseTimestamp(XMLGregorianCalendar value) {
        this.responseTimestamp = value;
    }

    /**
     * Gets the value of the businessKeyPairs property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessKeyPairs }
     *     
     */
    public BusinessKeyPairs getBusinessKeyPairs() {
        return businessKeyPairs;
    }

    /**
     * Sets the value of the businessKeyPairs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessKeyPairs }
     *     
     */
    public void setBusinessKeyPairs(BusinessKeyPairs value) {
        this.businessKeyPairs = value;
    }

}
