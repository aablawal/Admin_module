
package com.unionbankng.future.authorizationserver.accountenquiry;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessKeyPairs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessKeyPairs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BusinessKey" type="{http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader}BusinessKeyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessKeyPairs", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader", propOrder = {
    "businessKey"
})
public class BusinessKeyPairs {

    @XmlElement(name = "BusinessKey", namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader")
    protected List<BusinessKeyType> businessKey;

    /**
     * Gets the value of the businessKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the businessKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBusinessKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BusinessKeyType }
     * 
     * 
     */
    public List<BusinessKeyType> getBusinessKey() {
        if (businessKey == null) {
            businessKey = new ArrayList<BusinessKeyType>();
        }
        return this.businessKey;
    }

}
