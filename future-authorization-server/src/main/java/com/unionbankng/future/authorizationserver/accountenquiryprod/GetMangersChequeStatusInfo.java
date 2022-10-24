
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getMangersChequeStatusInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getMangersChequeStatusInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="typ_instr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cod_routing_instr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ref_instr_no_stno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amt_instr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMangersChequeStatusInfo", propOrder = {
    "typInstr",
    "codRoutingInstr",
    "refInstrNoStno",
    "amtInstr"
})
public class GetMangersChequeStatusInfo {

    @XmlElement(name = "typ_instr")
    protected String typInstr;
    @XmlElement(name = "cod_routing_instr")
    protected String codRoutingInstr;
    @XmlElement(name = "ref_instr_no_stno")
    protected String refInstrNoStno;
    @XmlElement(name = "amt_instr")
    protected String amtInstr;

    /**
     * Gets the value of the typInstr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypInstr() {
        return typInstr;
    }

    /**
     * Sets the value of the typInstr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypInstr(String value) {
        this.typInstr = value;
    }

    /**
     * Gets the value of the codRoutingInstr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodRoutingInstr() {
        return codRoutingInstr;
    }

    /**
     * Sets the value of the codRoutingInstr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodRoutingInstr(String value) {
        this.codRoutingInstr = value;
    }

    /**
     * Gets the value of the refInstrNoStno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefInstrNoStno() {
        return refInstrNoStno;
    }

    /**
     * Sets the value of the refInstrNoStno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefInstrNoStno(String value) {
        this.refInstrNoStno = value;
    }

    /**
     * Gets the value of the amtInstr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmtInstr() {
        return amtInstr;
    }

    /**
     * Sets the value of the amtInstr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmtInstr(String value) {
        this.amtInstr = value;
    }

}
