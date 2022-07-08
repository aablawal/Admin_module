
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for productList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="productList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cod_prod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moduleId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nam_product" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "productList", propOrder = {
    "codProd",
    "moduleId",
    "namProduct"
})
public class ProductList {

    @XmlElement(name = "cod_prod")
    protected String codProd;
    protected String moduleId;
    @XmlElement(name = "nam_product")
    protected String namProduct;

    /**
     * Gets the value of the codProd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodProd() {
        return codProd;
    }

    /**
     * Sets the value of the codProd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodProd(String value) {
        this.codProd = value;
    }

    /**
     * Gets the value of the moduleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * Sets the value of the moduleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleId(String value) {
        this.moduleId = value;
    }

    /**
     * Gets the value of the namProduct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamProduct() {
        return namProduct;
    }

    /**
     * Sets the value of the namProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamProduct(String value) {
        this.namProduct = value;
    }

}
