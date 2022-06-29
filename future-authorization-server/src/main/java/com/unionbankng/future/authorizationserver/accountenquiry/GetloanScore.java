
package com.unionbankng.future.authorizationserver.accountenquiry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getloanScore complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getloanScore">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="application_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="account_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="applicant_age" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="marital_status" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="home_type" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="home_resident_duration" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="no_of_dependents" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="gross_annual_income" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="highest_attained_education_lev" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="applied_ltv" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="contactability" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="repayment_bank_acct_domicile" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="loan_term_requested" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="monthly_repayment_salary_amt" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="designation" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="category_of_current_employer" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="no_of_year_at_curr_employer" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ever_90_in_past_12_mths_in_bur" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ever_suite_filed_or_wroff_bur" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="total_loan_outst_vs_grs_an_inc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getloanScore", propOrder = {
    "applicationNo",
    "accountNo",
    "applicantAge",
    "maritalStatus",
    "homeType",
    "gender",
    "homeResidentDuration",
    "noOfDependents",
    "grossAnnualIncome",
    "highestAttainedEducationLev",
    "appliedLtv",
    "contactability",
    "repaymentBankAcctDomicile",
    "loanTermRequested",
    "monthlyRepaymentSalaryAmt",
    "designation",
    "categoryOfCurrentEmployer",
    "noOfYearAtCurrEmployer",
    "ever90InPast12MthsInBur",
    "everSuiteFiledOrWroffBur",
    "totalLoanOutstVsGrsAnInc"
})
public class GetloanScore {

    @XmlElement(name = "application_no")
    protected String applicationNo;
    @XmlElement(name = "account_no")
    protected String accountNo;
    @XmlElement(name = "applicant_age")
    protected Integer applicantAge;
    @XmlElement(name = "marital_status")
    protected Integer maritalStatus;
    @XmlElement(name = "home_type")
    protected Integer homeType;
    protected Integer gender;
    @XmlElement(name = "home_resident_duration")
    protected Integer homeResidentDuration;
    @XmlElement(name = "no_of_dependents")
    protected Integer noOfDependents;
    @XmlElement(name = "gross_annual_income")
    protected Integer grossAnnualIncome;
    @XmlElement(name = "highest_attained_education_lev")
    protected Integer highestAttainedEducationLev;
    @XmlElement(name = "applied_ltv")
    protected Integer appliedLtv;
    protected Integer contactability;
    @XmlElement(name = "repayment_bank_acct_domicile")
    protected Integer repaymentBankAcctDomicile;
    @XmlElement(name = "loan_term_requested")
    protected Integer loanTermRequested;
    @XmlElement(name = "monthly_repayment_salary_amt")
    protected Integer monthlyRepaymentSalaryAmt;
    protected Integer designation;
    @XmlElement(name = "category_of_current_employer")
    protected Integer categoryOfCurrentEmployer;
    @XmlElement(name = "no_of_year_at_curr_employer")
    protected Integer noOfYearAtCurrEmployer;
    @XmlElement(name = "ever_90_in_past_12_mths_in_bur")
    protected Integer ever90InPast12MthsInBur;
    @XmlElement(name = "ever_suite_filed_or_wroff_bur")
    protected Integer everSuiteFiledOrWroffBur;
    @XmlElement(name = "total_loan_outst_vs_grs_an_inc")
    protected Integer totalLoanOutstVsGrsAnInc;

    /**
     * Gets the value of the applicationNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationNo() {
        return applicationNo;
    }

    /**
     * Sets the value of the applicationNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationNo(String value) {
        this.applicationNo = value;
    }

    /**
     * Gets the value of the accountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * Sets the value of the accountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNo(String value) {
        this.accountNo = value;
    }

    /**
     * Gets the value of the applicantAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApplicantAge() {
        return applicantAge;
    }

    /**
     * Sets the value of the applicantAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApplicantAge(Integer value) {
        this.applicantAge = value;
    }

    /**
     * Gets the value of the maritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the value of the maritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaritalStatus(Integer value) {
        this.maritalStatus = value;
    }

    /**
     * Gets the value of the homeType property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHomeType() {
        return homeType;
    }

    /**
     * Sets the value of the homeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHomeType(Integer value) {
        this.homeType = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGender(Integer value) {
        this.gender = value;
    }

    /**
     * Gets the value of the homeResidentDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHomeResidentDuration() {
        return homeResidentDuration;
    }

    /**
     * Sets the value of the homeResidentDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHomeResidentDuration(Integer value) {
        this.homeResidentDuration = value;
    }

    /**
     * Gets the value of the noOfDependents property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoOfDependents() {
        return noOfDependents;
    }

    /**
     * Sets the value of the noOfDependents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoOfDependents(Integer value) {
        this.noOfDependents = value;
    }

    /**
     * Gets the value of the grossAnnualIncome property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGrossAnnualIncome() {
        return grossAnnualIncome;
    }

    /**
     * Sets the value of the grossAnnualIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGrossAnnualIncome(Integer value) {
        this.grossAnnualIncome = value;
    }

    /**
     * Gets the value of the highestAttainedEducationLev property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHighestAttainedEducationLev() {
        return highestAttainedEducationLev;
    }

    /**
     * Sets the value of the highestAttainedEducationLev property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHighestAttainedEducationLev(Integer value) {
        this.highestAttainedEducationLev = value;
    }

    /**
     * Gets the value of the appliedLtv property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAppliedLtv() {
        return appliedLtv;
    }

    /**
     * Sets the value of the appliedLtv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAppliedLtv(Integer value) {
        this.appliedLtv = value;
    }

    /**
     * Gets the value of the contactability property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getContactability() {
        return contactability;
    }

    /**
     * Sets the value of the contactability property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setContactability(Integer value) {
        this.contactability = value;
    }

    /**
     * Gets the value of the repaymentBankAcctDomicile property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRepaymentBankAcctDomicile() {
        return repaymentBankAcctDomicile;
    }

    /**
     * Sets the value of the repaymentBankAcctDomicile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRepaymentBankAcctDomicile(Integer value) {
        this.repaymentBankAcctDomicile = value;
    }

    /**
     * Gets the value of the loanTermRequested property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLoanTermRequested() {
        return loanTermRequested;
    }

    /**
     * Sets the value of the loanTermRequested property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLoanTermRequested(Integer value) {
        this.loanTermRequested = value;
    }

    /**
     * Gets the value of the monthlyRepaymentSalaryAmt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMonthlyRepaymentSalaryAmt() {
        return monthlyRepaymentSalaryAmt;
    }

    /**
     * Sets the value of the monthlyRepaymentSalaryAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMonthlyRepaymentSalaryAmt(Integer value) {
        this.monthlyRepaymentSalaryAmt = value;
    }

    /**
     * Gets the value of the designation property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDesignation() {
        return designation;
    }

    /**
     * Sets the value of the designation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDesignation(Integer value) {
        this.designation = value;
    }

    /**
     * Gets the value of the categoryOfCurrentEmployer property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCategoryOfCurrentEmployer() {
        return categoryOfCurrentEmployer;
    }

    /**
     * Sets the value of the categoryOfCurrentEmployer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCategoryOfCurrentEmployer(Integer value) {
        this.categoryOfCurrentEmployer = value;
    }

    /**
     * Gets the value of the noOfYearAtCurrEmployer property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoOfYearAtCurrEmployer() {
        return noOfYearAtCurrEmployer;
    }

    /**
     * Sets the value of the noOfYearAtCurrEmployer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoOfYearAtCurrEmployer(Integer value) {
        this.noOfYearAtCurrEmployer = value;
    }

    /**
     * Gets the value of the ever90InPast12MthsInBur property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEver90InPast12MthsInBur() {
        return ever90InPast12MthsInBur;
    }

    /**
     * Sets the value of the ever90InPast12MthsInBur property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEver90InPast12MthsInBur(Integer value) {
        this.ever90InPast12MthsInBur = value;
    }

    /**
     * Gets the value of the everSuiteFiledOrWroffBur property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEverSuiteFiledOrWroffBur() {
        return everSuiteFiledOrWroffBur;
    }

    /**
     * Sets the value of the everSuiteFiledOrWroffBur property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEverSuiteFiledOrWroffBur(Integer value) {
        this.everSuiteFiledOrWroffBur = value;
    }

    /**
     * Gets the value of the totalLoanOutstVsGrsAnInc property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalLoanOutstVsGrsAnInc() {
        return totalLoanOutstVsGrsAnInc;
    }

    /**
     * Sets the value of the totalLoanOutstVsGrsAnInc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalLoanOutstVsGrsAnInc(Integer value) {
        this.totalLoanOutstVsGrsAnInc = value;
    }

}
