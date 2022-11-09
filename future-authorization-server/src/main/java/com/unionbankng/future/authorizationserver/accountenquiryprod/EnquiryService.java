
package com.unionbankng.future.authorizationserver.accountenquiryprod;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import java.util.List;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "EnquiryService", targetNamespace = "http://service.unionbank.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface EnquiryService {


    /**
     * 
     * @param acctsno
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.CustomerAcctsInfo
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAccountSummary", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSummary")
    @ResponseWrapper(localName = "getAccountSummaryResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSummaryResponse")
    public CustomerAcctsInfo getAccountSummary(
        @WebParam(name = "ACCTSNO", targetNamespace = "")
        String acctsno);

    /**
     * 
     * @param customerID
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.CustomerAcctsInfo
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAccountSummaryByCustomerID", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSummaryByCustomerID")
    @ResponseWrapper(localName = "getAccountSummaryByCustomerIDResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSummaryByCustomerIDResponse")
    public CustomerAcctsInfo getAccountSummaryByCustomerID(
        @WebParam(name = "CustomerID", targetNamespace = "")
        String customerID);

    /**
     * 
     * @param custID
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.ClientDetails
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCustomerDetails", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetails")
    @ResponseWrapper(localName = "getCustomerDetailsResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailsResponse")
    public ClientDetails getCustomerDetails(
        @WebParam(name = "CustID", targetNamespace = "")
        String custID);

    /**
     * 
     * @param acctsno
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.ClientDetails
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCustomerDetailsWithAcctNumber", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailsWithAcctNumber")
    @ResponseWrapper(localName = "getCustomerDetailsWithAcctNumberResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailsWithAcctNumberResponse")
    public ClientDetails getCustomerDetailsWithAcctNumber(
        @WebParam(name = "ACCTSNO", targetNamespace = "")
        String acctsno);

    /**
     * 
     * @param firstName
     * @param lastName
     * @return
     *     returns java.util.List<com.unionbankng.marcus.accountenquiryprod.ClientDetails>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCustomerDetailswithFirstandLast", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailswithFirstandLast")
    @ResponseWrapper(localName = "getCustomerDetailswithFirstandLastResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailswithFirstandLastResponse")
    public List<ClientDetails> getCustomerDetailswithFirstandLast(
        @WebParam(name = "firstName", targetNamespace = "")
        String firstName,
        @WebParam(name = "lastName", targetNamespace = "")
        String lastName);

    /**
     * 
     * @param shortName
     * @return
     *     returns java.util.List<com.unionbankng.marcus.accountenquiryprod.ClientDetails>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCustomerDetailsbyShortName", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailsbyShortName")
    @ResponseWrapper(localName = "getCustomerDetailsbyShortNameResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailsbyShortNameResponse")
    public List<ClientDetails> getCustomerDetailsbyShortName(
        @WebParam(name = "shortName", targetNamespace = "")
        String shortName);

    /**
     * 
     * @param phoneNumber
     * @return
     *     returns java.util.List<com.unionbankng.marcus.accountenquiryprod.ClientDetails>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCustomerDetailsbyPhoneNumber", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailsbyPhoneNumber")
    @ResponseWrapper(localName = "getCustomerDetailsbyPhoneNumberResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailsbyPhoneNumberResponse")
    public List<ClientDetails> getCustomerDetailsbyPhoneNumber(
        @WebParam(name = "phoneNumber", targetNamespace = "")
        String phoneNumber);

    /**
     * 
     * @param acctsno
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.NameEnquiryResponses
     */
    @WebMethod(operationName = "NameEnquiry")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "NameEnquiry", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.NameEnquiry")
    @ResponseWrapper(localName = "NameEnquiryResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.NameEnquiryResponse")
    public NameEnquiryResponses nameEnquiry(
        @WebParam(name = "ACCTSNO", targetNamespace = "")
        String acctsno);

    /**
     * 
     * @param acctsno
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.CustomerAcctsDetail
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCustomerAcctsDetail", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerAcctsDetail")
    @ResponseWrapper(localName = "getCustomerAcctsDetailResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerAcctsDetailResponse")
    public CustomerAcctsDetail getCustomerAcctsDetail(
        @WebParam(name = "ACCTSNO", targetNamespace = "")
        String acctsno);

    /**
     * 
     * @param typInstr
     * @param refInstrNoStno
     * @param codRoutingInstr
     * @param amtInstr
     * @return
     *     returns java.util.List<com.unionbankng.marcus.accountenquiryprod.ManagersChequeDetails>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getMangersChequeStatusInfo", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetMangersChequeStatusInfo")
    @ResponseWrapper(localName = "getMangersChequeStatusInfoResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetMangersChequeStatusInfoResponse")
    public List<ManagersChequeDetails> getMangersChequeStatusInfo(
        @WebParam(name = "typ_instr", targetNamespace = "")
        String typInstr,
        @WebParam(name = "cod_routing_instr", targetNamespace = "")
        String codRoutingInstr,
        @WebParam(name = "ref_instr_no_stno", targetNamespace = "")
        String refInstrNoStno,
        @WebParam(name = "amt_instr", targetNamespace = "")
        String amtInstr);

    /**
     * 
     * @param typInstr
     * @param refInstrNoStno
     * @param codRoutingInstr
     * @param amtInstr
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getMangersChequeStatus", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetMangersChequeStatus")
    @ResponseWrapper(localName = "getMangersChequeStatusResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetMangersChequeStatusResponse")
    public String getMangersChequeStatus(
        @WebParam(name = "typ_instr", targetNamespace = "")
        String typInstr,
        @WebParam(name = "cod_routing_instr", targetNamespace = "")
        String codRoutingInstr,
        @WebParam(name = "ref_instr_no_stno", targetNamespace = "")
        String refInstrNoStno,
        @WebParam(name = "amt_instr", targetNamespace = "")
        String amtInstr);

    /**
     * 
     * @param strAccountNo
     * @param strChequeNo
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getChequeNumberInquiry", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetChequeNumberInquiry")
    @ResponseWrapper(localName = "getChequeNumberInquiryResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetChequeNumberInquiryResponse")
    public String getChequeNumberInquiry(
        @WebParam(name = "strAccountNo", targetNamespace = "")
        String strAccountNo,
        @WebParam(name = "strChequeNo", targetNamespace = "")
        String strChequeNo);

    /**
     * 
     * @param acctno
     * @param enddate
     * @param startdate
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.CustomerAcctsWithTransactionInfoWithBranch
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAccountSummaryANDTransactions", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSummaryANDTransactions")
    @ResponseWrapper(localName = "getAccountSummaryANDTransactionsResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSummaryANDTransactionsResponse")
    public CustomerAcctsWithTransactionInfoWithBranch getAccountSummaryANDTransactions(
        @WebParam(name = "ACCTNO", targetNamespace = "")
        String acctno,
        @WebParam(name = "STARTDATE", targetNamespace = "")
        String startdate,
        @WebParam(name = "ENDDATE", targetNamespace = "")
        String enddate);

    /**
     * 
     * @param acctno
     * @param enddate
     * @param startdate
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.CustomerAcctsWithTranInfoWithBranchGeneric
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAccountSummaryANDTxnGeneric", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSummaryANDTxnGeneric")
    @ResponseWrapper(localName = "getAccountSummaryANDTxnGenericResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSummaryANDTxnGenericResponse")
    public CustomerAcctsWithTranInfoWithBranchGeneric getAccountSummaryANDTxnGeneric(
        @WebParam(name = "ACCTNO", targetNamespace = "")
        String acctno,
        @WebParam(name = "STARTDATE", targetNamespace = "")
        String startdate,
        @WebParam(name = "ENDDATE", targetNamespace = "")
        String enddate);

    /**
     * 
     * @param custID
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.AccountMemoInfo
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAccounMemoInfo", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccounMemoInfo")
    @ResponseWrapper(localName = "getAccounMemoInfoResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccounMemoInfoResponse")
    public AccountMemoInfo getAccounMemoInfo(
        @WebParam(name = "CustID", targetNamespace = "")
        String custID);

    /**
     * 
     * @param acctnumber
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.AccountSearchInfo
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAccountSerachByAccountNumber", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSerachByAccountNumber")
    @ResponseWrapper(localName = "getAccountSerachByAccountNumberResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSerachByAccountNumberResponse")
    public AccountSearchInfo getAccountSerachByAccountNumber(
        @WebParam(name = "ACCTNUMBER", targetNamespace = "")
        String acctnumber);

    /**
     * 
     * @param accttitle
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.AccountSearchInfo
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAccountSerachByAccountTitle", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSerachByAccountTitle")
    @ResponseWrapper(localName = "getAccountSerachByAccountTitleResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetAccountSerachByAccountTitleResponse")
    public AccountSearchInfo getAccountSerachByAccountTitle(
        @WebParam(name = "ACCTTITLE", targetNamespace = "")
        String accttitle);

    /**
     * 
     * @param accttitle
     * @param brncode
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.AccountSearchInfo
     */
    @WebMethod(operationName = "AccountSearchByAcctTitleAndBranchCode")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "AccountSearchByAcctTitleAndBranchCode", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.AccountSearchByAcctTitleAndBranchCode")
    @ResponseWrapper(localName = "AccountSearchByAcctTitleAndBranchCodeResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.AccountSearchByAcctTitleAndBranchCodeResponse")
    public AccountSearchInfo accountSearchByAcctTitleAndBranchCode(
        @WebParam(name = "ACCTTITLE", targetNamespace = "")
        String accttitle,
        @WebParam(name = "BRNCODE", targetNamespace = "")
        String brncode);

    /**
     * 
     * @param acctno
     * @param tranDate
     * @param rowId
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.SingleTransactionDetails
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSingleTransactionDetails", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetSingleTransactionDetails")
    @ResponseWrapper(localName = "getSingleTransactionDetailsResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetSingleTransactionDetailsResponse")
    public SingleTransactionDetails getSingleTransactionDetails(
        @WebParam(name = "ACCTNO", targetNamespace = "")
        String acctno,
        @WebParam(name = "TranDate", targetNamespace = "")
        String tranDate,
        @WebParam(name = "RowId", targetNamespace = "")
        String rowId);

    /**
     * 
     * @param createdBy
     * @param serverIp
     * @param eventRef
     * @param event
     * @param staffId
     */
    @WebMethod
    @RequestWrapper(localName = "createLog", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.CreateLog")
    @ResponseWrapper(localName = "createLogResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.CreateLogResponse")
    public void createLog(
        @WebParam(name = "staffId", targetNamespace = "")
        String staffId,
        @WebParam(name = "event", targetNamespace = "")
        String event,
        @WebParam(name = "createdBy", targetNamespace = "")
        String createdBy,
        @WebParam(name = "eventRef", targetNamespace = "")
        String eventRef,
        @WebParam(name = "serverIp", targetNamespace = "")
        String serverIp);

    /**
     * 
     * @param endDate
     * @param userId
     * @param startDate
     * @return
     *     returns java.util.List<com.unionbankng.marcus.accountenquiryprod.AuditInformation>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getActivityLogSpecific", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetActivityLogSpecific")
    @ResponseWrapper(localName = "getActivityLogSpecificResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetActivityLogSpecificResponse")
    public List<AuditInformation> getActivityLogSpecific(
        @WebParam(name = "userId", targetNamespace = "")
        String userId,
        @WebParam(name = "startDate", targetNamespace = "")
        String startDate,
        @WebParam(name = "endDate", targetNamespace = "")
        String endDate);

    /**
     * 
     * @param endDate
     * @param startDate
     * @return
     *     returns java.util.List<com.unionbankng.marcus.accountenquiryprod.AuditInformation>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getActivityLog", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetActivityLog")
    @ResponseWrapper(localName = "getActivityLogResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetActivityLogResponse")
    public List<AuditInformation> getActivityLog(
        @WebParam(name = "startDate", targetNamespace = "")
        String startDate,
        @WebParam(name = "endDate", targetNamespace = "")
        String endDate);

    /**
     * 
     * @return
     *     returns java.util.List<com.unionbankng.marcus.accountenquiryprod.BranchList>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBranchList", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetBranchList")
    @ResponseWrapper(localName = "getBranchListResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetBranchListResponse")
    public List<BranchList> getBranchList();

    /**
     * 
     * @param accountNumber
     * @return
     *     returns java.util.List<com.unionbankng.marcus.accountenquiryprod.LoanList>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getLoanList", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetLoanList")
    @ResponseWrapper(localName = "getLoanListResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetLoanListResponse")
    public List<LoanList> getLoanList(
        @WebParam(name = "accountNumber", targetNamespace = "")
        String accountNumber);

    /**
     * 
     * @param accountNumber
     * @return
     *     returns java.util.List<com.unionbankng.marcus.accountenquiryprod.OverdraftList>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getOverDraftList", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetOverDraftList")
    @ResponseWrapper(localName = "getOverDraftListResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetOverDraftListResponse")
    public List<OverdraftList> getOverDraftList(
        @WebParam(name = "accountNumber", targetNamespace = "")
        String accountNumber);

    /**
     * 
     * @param cic
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getcicRecord", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetcicRecord")
    @ResponseWrapper(localName = "getcicRecordResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetcicRecordResponse")
    public String getcicRecord(
        @WebParam(name = "cic", targetNamespace = "")
        String cic);

    /**
     * 
     * @param terminalId
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.GlTerminalDetails
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getGlForTerminal", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetGlForTerminal")
    @ResponseWrapper(localName = "getGlForTerminalResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetGlForTerminalResponse")
    public GlTerminalDetails getGlForTerminal(
        @WebParam(name = "terminalId", targetNamespace = "")
        String terminalId);

    /**
     * 
     * @param bvn
     * @return
     *     returns java.util.List<com.unionbankng.marcus.accountenquiryprod.ClientDetails>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCustomerDetailswithBvn", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailswithBvn")
    @ResponseWrapper(localName = "getCustomerDetailswithBvnResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailswithBvnResponse")
    public List<ClientDetails> getCustomerDetailswithBvn(
        @WebParam(name = "Bvn", targetNamespace = "")
        String bvn);

    /**
     * 
     * @param custID
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.ClientDetails
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCustomerDetailsUpdate", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailsUpdate")
    @ResponseWrapper(localName = "getCustomerDetailsUpdateResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailsUpdateResponse")
    public ClientDetails getCustomerDetailsUpdate(
        @WebParam(name = "CustID", targetNamespace = "")
        String custID);

    /**
     * 
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param bvn
     * @return
     *     returns com.unionbankng.marcus.accountenquiryprod.ClientDetails2
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCustomerDetailsByNamesBvn", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailsByNamesBvn")
    @ResponseWrapper(localName = "getCustomerDetailsByNamesBvnResponse", targetNamespace = "http://service.unionbank.com/", className = "com.unionbankng.marcus.accountenquiryprod.GetCustomerDetailsByNamesBvnResponse")
    public ClientDetails2 getCustomerDetailsByNamesBvn(
        @WebParam(name = "firstName", targetNamespace = "")
        String firstName,
        @WebParam(name = "lastName", targetNamespace = "")
        String lastName,
        @WebParam(name = "BVN", targetNamespace = "")
        String bvn,
        @WebParam(name = "dateOfBirth", targetNamespace = "")
        String dateOfBirth);

}