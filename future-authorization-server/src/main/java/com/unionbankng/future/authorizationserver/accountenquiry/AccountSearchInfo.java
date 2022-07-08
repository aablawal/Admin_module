
package com.unionbankng.future.authorizationserver.accountenquiry;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accountSearchInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accountSearchInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountSearchByAcctTitle" type="{http://service.unionbank.com/}accountSearchByAcctTitle" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accountSearchInfo", propOrder = {
    "accountSearchByAcctTitle",
    "description"
})
public class AccountSearchInfo {

    @XmlElement(nillable = true)
    protected List<AccountSearchByAcctTitle> accountSearchByAcctTitle;
    protected String description;

    /**
     * Gets the value of the accountSearchByAcctTitle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accountSearchByAcctTitle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccountSearchByAcctTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccountSearchByAcctTitle }
     * 
     * 
     */
    public List<AccountSearchByAcctTitle> getAccountSearchByAcctTitle() {
        if (accountSearchByAcctTitle == null) {
            accountSearchByAcctTitle = new ArrayList<AccountSearchByAcctTitle>();
        }
        return this.accountSearchByAcctTitle;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
