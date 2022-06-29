
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accountMandate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accountMandate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cifid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filetype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="imgtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recordstat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seqno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sig_desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sig_text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sigid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specimensqno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accountMandate", propOrder = {
    "cifid",
    "filetype",
    "imgtype",
    "recordstat",
    "seqno",
    "sig",
    "sigDesc",
    "sigText",
    "sigid",
    "specimensqno",
    "status"
})
public class AccountMandate {

    protected String cifid;
    protected String filetype;
    protected String imgtype;
    protected String recordstat;
    protected String seqno;
    protected String sig;
    @XmlElement(name = "sig_desc")
    protected String sigDesc;
    @XmlElement(name = "sig_text")
    protected String sigText;
    protected String sigid;
    protected String specimensqno;
    protected String status;

    /**
     * Gets the value of the cifid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCifid() {
        return cifid;
    }

    /**
     * Sets the value of the cifid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCifid(String value) {
        this.cifid = value;
    }

    /**
     * Gets the value of the filetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiletype() {
        return filetype;
    }

    /**
     * Sets the value of the filetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiletype(String value) {
        this.filetype = value;
    }

    /**
     * Gets the value of the imgtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImgtype() {
        return imgtype;
    }

    /**
     * Sets the value of the imgtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImgtype(String value) {
        this.imgtype = value;
    }

    /**
     * Gets the value of the recordstat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordstat() {
        return recordstat;
    }

    /**
     * Sets the value of the recordstat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordstat(String value) {
        this.recordstat = value;
    }

    /**
     * Gets the value of the seqno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeqno() {
        return seqno;
    }

    /**
     * Sets the value of the seqno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeqno(String value) {
        this.seqno = value;
    }

    /**
     * Gets the value of the sig property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSig() {
        return sig;
    }

    /**
     * Sets the value of the sig property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSig(String value) {
        this.sig = value;
    }

    /**
     * Gets the value of the sigDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigDesc() {
        return sigDesc;
    }

    /**
     * Sets the value of the sigDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigDesc(String value) {
        this.sigDesc = value;
    }

    /**
     * Gets the value of the sigText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigText() {
        return sigText;
    }

    /**
     * Sets the value of the sigText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigText(String value) {
        this.sigText = value;
    }

    /**
     * Gets the value of the sigid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigid() {
        return sigid;
    }

    /**
     * Sets the value of the sigid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigid(String value) {
        this.sigid = value;
    }

    /**
     * Gets the value of the specimensqno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecimensqno() {
        return specimensqno;
    }

    /**
     * Sets the value of the specimensqno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecimensqno(String value) {
        this.specimensqno = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
