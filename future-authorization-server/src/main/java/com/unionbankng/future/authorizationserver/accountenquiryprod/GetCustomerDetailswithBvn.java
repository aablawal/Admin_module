
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCustomerDetailswithBvn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCustomerDetailswithBvn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Bvn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCustomerDetailswithBvn", propOrder = {
    "bvn"
})
public class GetCustomerDetailswithBvn {

    @XmlElement(name = "Bvn")
    protected String bvn;

    /**
     * Gets the value of the bvn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBvn() {
        return bvn;
    }

    /**
     * Sets the value of the bvn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBvn(String value) {
        this.bvn = value;
    }

}
