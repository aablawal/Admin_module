
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getAccounMemoInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getAccounMemoInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAccounMemoInfo", propOrder = {
    "custID"
})
public class GetAccounMemoInfo {

    @XmlElement(name = "CustID")
    protected String custID;

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

}
