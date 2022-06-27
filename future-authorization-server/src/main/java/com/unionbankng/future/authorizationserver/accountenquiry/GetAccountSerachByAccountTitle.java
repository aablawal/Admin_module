
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getAccountSerachByAccountTitle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getAccountSerachByAccountTitle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ACCTTITLE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAccountSerachByAccountTitle", propOrder = {
    "accttitle"
})
public class GetAccountSerachByAccountTitle {

    @XmlElement(name = "ACCTTITLE")
    protected String accttitle;

    /**
     * Gets the value of the accttitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACCTTITLE() {
        return accttitle;
    }

    /**
     * Sets the value of the accttitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACCTTITLE(String value) {
        this.accttitle = value;
    }

}
