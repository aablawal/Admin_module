
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getcicRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getcicRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getcicRecord", propOrder = {
    "cic"
})
public class GetcicRecord {

    protected String cic;

    /**
     * Gets the value of the cic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCic() {
        return cic;
    }

    /**
     * Sets the value of the cic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCic(String value) {
        this.cic = value;
    }

}
