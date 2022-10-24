
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for clientDetails2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clientDetails2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="address_Line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="branchName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessRegNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="custEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="custFax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="custNationality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="custPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="custType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="custid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customer_Bvn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datCustOpen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateIncoporated" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="director1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="director2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="director3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="director4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="director5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flgCustType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maillingAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maritalStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nam_Cust_First" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nam_Cust_Last" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nam_Cust_Mid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nam_Custadr_City" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nam_Custadr_State" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nam_Signatory5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nationalityID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permanentAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spouseName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="txt_Cust_Prefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="txt_Custadr_Add2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="txt_Custadr_Add3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clientDetails2", propOrder = {
    "cic",
    "addressLine1",
    "branchName",
    "businessCategory",
    "businessRegNo",
    "custEmail",
    "custFax",
    "custNationality",
    "custPhone",
    "custType",
    "custid",
    "customerName",
    "customerBvn",
    "datCustOpen",
    "dateIncoporated",
    "dateOfBirth",
    "description",
    "director1",
    "director2",
    "director3",
    "director4",
    "director5",
    "flgCustType",
    "gender",
    "maillingAddress",
    "maritalStatus",
    "namCustFirst",
    "namCustLast",
    "namCustMid",
    "namCustadrCity",
    "namCustadrState",
    "namSignatory5",
    "nationalityID",
    "permanentAddress",
    "spouseName",
    "txtCustPrefix",
    "txtCustadrAdd2",
    "txtCustadrAdd3"
})
public class ClientDetails2 {

    protected String cic;
    @XmlElement(name = "address_Line1")
    protected String addressLine1;
    protected String branchName;
    protected String businessCategory;
    protected String businessRegNo;
    protected String custEmail;
    protected String custFax;
    protected String custNationality;
    protected String custPhone;
    protected String custType;
    protected String custid;
    protected String customerName;
    @XmlElement(name = "customer_Bvn")
    protected String customerBvn;
    protected String datCustOpen;
    protected String dateIncoporated;
    protected String dateOfBirth;
    protected String description;
    protected String director1;
    protected String director2;
    protected String director3;
    protected String director4;
    protected String director5;
    protected String flgCustType;
    protected String gender;
    protected String maillingAddress;
    protected String maritalStatus;
    @XmlElement(name = "nam_Cust_First")
    protected String namCustFirst;
    @XmlElement(name = "nam_Cust_Last")
    protected String namCustLast;
    @XmlElement(name = "nam_Cust_Mid")
    protected String namCustMid;
    @XmlElement(name = "nam_Custadr_City")
    protected String namCustadrCity;
    @XmlElement(name = "nam_Custadr_State")
    protected String namCustadrState;
    @XmlElement(name = "nam_Signatory5")
    protected String namSignatory5;
    protected String nationalityID;
    protected String permanentAddress;
    protected String spouseName;
    @XmlElement(name = "txt_Cust_Prefix")
    protected String txtCustPrefix;
    @XmlElement(name = "txt_Custadr_Add2")
    protected String txtCustadrAdd2;
    @XmlElement(name = "txt_Custadr_Add3")
    protected String txtCustadrAdd3;

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

    /**
     * Gets the value of the addressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the value of the addressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine1(String value) {
        this.addressLine1 = value;
    }

    /**
     * Gets the value of the branchName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * Sets the value of the branchName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchName(String value) {
        this.branchName = value;
    }

    /**
     * Gets the value of the businessCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessCategory() {
        return businessCategory;
    }

    /**
     * Sets the value of the businessCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessCategory(String value) {
        this.businessCategory = value;
    }

    /**
     * Gets the value of the businessRegNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessRegNo() {
        return businessRegNo;
    }

    /**
     * Sets the value of the businessRegNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessRegNo(String value) {
        this.businessRegNo = value;
    }

    /**
     * Gets the value of the custEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustEmail() {
        return custEmail;
    }

    /**
     * Sets the value of the custEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustEmail(String value) {
        this.custEmail = value;
    }

    /**
     * Gets the value of the custFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustFax() {
        return custFax;
    }

    /**
     * Sets the value of the custFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustFax(String value) {
        this.custFax = value;
    }

    /**
     * Gets the value of the custNationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustNationality() {
        return custNationality;
    }

    /**
     * Sets the value of the custNationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustNationality(String value) {
        this.custNationality = value;
    }

    /**
     * Gets the value of the custPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustPhone() {
        return custPhone;
    }

    /**
     * Sets the value of the custPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustPhone(String value) {
        this.custPhone = value;
    }

    /**
     * Gets the value of the custType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustType() {
        return custType;
    }

    /**
     * Sets the value of the custType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustType(String value) {
        this.custType = value;
    }

    /**
     * Gets the value of the custid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustid() {
        return custid;
    }

    /**
     * Sets the value of the custid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustid(String value) {
        this.custid = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the customerBvn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerBvn() {
        return customerBvn;
    }

    /**
     * Sets the value of the customerBvn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerBvn(String value) {
        this.customerBvn = value;
    }

    /**
     * Gets the value of the datCustOpen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatCustOpen() {
        return datCustOpen;
    }

    /**
     * Sets the value of the datCustOpen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatCustOpen(String value) {
        this.datCustOpen = value;
    }

    /**
     * Gets the value of the dateIncoporated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateIncoporated() {
        return dateIncoporated;
    }

    /**
     * Sets the value of the dateIncoporated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateIncoporated(String value) {
        this.dateIncoporated = value;
    }

    /**
     * Gets the value of the dateOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfBirth(String value) {
        this.dateOfBirth = value;
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

    /**
     * Gets the value of the director1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirector1() {
        return director1;
    }

    /**
     * Sets the value of the director1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirector1(String value) {
        this.director1 = value;
    }

    /**
     * Gets the value of the director2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirector2() {
        return director2;
    }

    /**
     * Sets the value of the director2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirector2(String value) {
        this.director2 = value;
    }

    /**
     * Gets the value of the director3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirector3() {
        return director3;
    }

    /**
     * Sets the value of the director3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirector3(String value) {
        this.director3 = value;
    }

    /**
     * Gets the value of the director4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirector4() {
        return director4;
    }

    /**
     * Sets the value of the director4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirector4(String value) {
        this.director4 = value;
    }

    /**
     * Gets the value of the director5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirector5() {
        return director5;
    }

    /**
     * Sets the value of the director5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirector5(String value) {
        this.director5 = value;
    }

    /**
     * Gets the value of the flgCustType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlgCustType() {
        return flgCustType;
    }

    /**
     * Sets the value of the flgCustType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlgCustType(String value) {
        this.flgCustType = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the maillingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaillingAddress() {
        return maillingAddress;
    }

    /**
     * Sets the value of the maillingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaillingAddress(String value) {
        this.maillingAddress = value;
    }

    /**
     * Gets the value of the maritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the value of the maritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaritalStatus(String value) {
        this.maritalStatus = value;
    }

    /**
     * Gets the value of the namCustFirst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamCustFirst() {
        return namCustFirst;
    }

    /**
     * Sets the value of the namCustFirst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamCustFirst(String value) {
        this.namCustFirst = value;
    }

    /**
     * Gets the value of the namCustLast property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamCustLast() {
        return namCustLast;
    }

    /**
     * Sets the value of the namCustLast property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamCustLast(String value) {
        this.namCustLast = value;
    }

    /**
     * Gets the value of the namCustMid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamCustMid() {
        return namCustMid;
    }

    /**
     * Sets the value of the namCustMid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamCustMid(String value) {
        this.namCustMid = value;
    }

    /**
     * Gets the value of the namCustadrCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamCustadrCity() {
        return namCustadrCity;
    }

    /**
     * Sets the value of the namCustadrCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamCustadrCity(String value) {
        this.namCustadrCity = value;
    }

    /**
     * Gets the value of the namCustadrState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamCustadrState() {
        return namCustadrState;
    }

    /**
     * Sets the value of the namCustadrState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamCustadrState(String value) {
        this.namCustadrState = value;
    }

    /**
     * Gets the value of the namSignatory5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamSignatory5() {
        return namSignatory5;
    }

    /**
     * Sets the value of the namSignatory5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamSignatory5(String value) {
        this.namSignatory5 = value;
    }

    /**
     * Gets the value of the nationalityID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationalityID() {
        return nationalityID;
    }

    /**
     * Sets the value of the nationalityID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationalityID(String value) {
        this.nationalityID = value;
    }

    /**
     * Gets the value of the permanentAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermanentAddress() {
        return permanentAddress;
    }

    /**
     * Sets the value of the permanentAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermanentAddress(String value) {
        this.permanentAddress = value;
    }

    /**
     * Gets the value of the spouseName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpouseName() {
        return spouseName;
    }

    /**
     * Sets the value of the spouseName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseName(String value) {
        this.spouseName = value;
    }

    /**
     * Gets the value of the txtCustPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxtCustPrefix() {
        return txtCustPrefix;
    }

    /**
     * Sets the value of the txtCustPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxtCustPrefix(String value) {
        this.txtCustPrefix = value;
    }

    /**
     * Gets the value of the txtCustadrAdd2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxtCustadrAdd2() {
        return txtCustadrAdd2;
    }

    /**
     * Sets the value of the txtCustadrAdd2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxtCustadrAdd2(String value) {
        this.txtCustadrAdd2 = value;
    }

    /**
     * Gets the value of the txtCustadrAdd3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxtCustadrAdd3() {
        return txtCustadrAdd3;
    }

    /**
     * Sets the value of the txtCustadrAdd3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxtCustadrAdd3(String value) {
        this.txtCustadrAdd3 = value;
    }

}
