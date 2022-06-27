
package com.unionbankng.future.authorizationserver.accountenquiry;

import com.unionbankng.future.authorizationserver.accountenquiryprod.GetCustomerDetailsWithAcctNumber;
import com.unionbankng.future.authorizationserver.accountenquiryprod.GetloanScore;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.unionbankng.marcus.accountenquiry package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetChequeNumberInquiryResponse_QNAME = new QName("http://service.unionbank.com/", "getChequeNumberInquiryResponse");
    private final static QName _GetAccountSummaryANDTxnGeneric_QNAME = new QName("http://service.unionbank.com/", "getAccountSummaryANDTxnGeneric");
    private final static QName _AccountSearchByAcctTitleAndBranchCodeResponse_QNAME = new QName("http://service.unionbank.com/", "AccountSearchByAcctTitleAndBranchCodeResponse");
    private final static QName _GetMangersChequeStatusInfo_QNAME = new QName("http://service.unionbank.com/", "getMangersChequeStatusInfo");
    private final static QName _GetAccountMandateResponse_QNAME = new QName("http://service.unionbank.com/", "getAccountMandateResponse");
    private final static QName _UBNHeader_QNAME = new QName("http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader", "UBNHeader");
    private final static QName _GetAccounMemoInfoResponse_QNAME = new QName("http://service.unionbank.com/", "getAccounMemoInfoResponse");
    private final static QName _GetAccountSerachByAccountTitle_QNAME = new QName("http://service.unionbank.com/", "getAccountSerachByAccountTitle");
    private final static QName _GetCustomerDetailswithFirstandLast_QNAME = new QName("http://service.unionbank.com/", "getCustomerDetailswithFirstandLast");
    private final static QName _GetCustomerDetailsbyPhoneNumber_QNAME = new QName("http://service.unionbank.com/", "getCustomerDetailsbyPhoneNumber");
    private final static QName _GetCustomerMandateResponse_QNAME = new QName("http://service.unionbank.com/", "getCustomerMandateResponse");
    private final static QName _GetLoanListResponse_QNAME = new QName("http://service.unionbank.com/", "getLoanListResponse");
    private final static QName _GetCustomerDetailsResponse_QNAME = new QName("http://service.unionbank.com/", "getCustomerDetailsResponse");
    private final static QName _GetCustomerDetailswithFirstandLastResponse_QNAME = new QName("http://service.unionbank.com/", "getCustomerDetailswithFirstandLastResponse");
    private final static QName _GetOverDraftList_QNAME = new QName("http://service.unionbank.com/", "getOverDraftList");
    private final static QName _GetCustomerDetails_QNAME = new QName("http://service.unionbank.com/", "getCustomerDetails");
    private final static QName _GetAccountSerachByAccountNumber_QNAME = new QName("http://service.unionbank.com/", "getAccountSerachByAccountNumber");
    private final static QName _GetCustomerDetailsWithAcctNumberResponse_QNAME = new QName("http://service.unionbank.com/", "getCustomerDetailsWithAcctNumberResponse");
    private final static QName _GetAccountSerachByAccountNumberResponse_QNAME = new QName("http://service.unionbank.com/", "getAccountSerachByAccountNumberResponse");
    private final static QName _GetChqUtilizationResponse_QNAME = new QName("http://service.unionbank.com/", "getChqUtilizationResponse");
    private final static QName _GetCustomerAcctsDetailResponse_QNAME = new QName("http://service.unionbank.com/", "getCustomerAcctsDetailResponse");
    private final static QName _GetloanScore_QNAME = new QName("http://service.unionbank.com/", "getloanScore");
    private final static QName _GetOverDraftListResponse_QNAME = new QName("http://service.unionbank.com/", "getOverDraftListResponse");
    private final static QName _GetLoanList_QNAME = new QName("http://service.unionbank.com/", "getLoanList");
    private final static QName _GetAccountSummaryANDTransactions_QNAME = new QName("http://service.unionbank.com/", "getAccountSummaryANDTransactions");
    private final static QName _GetcicRecord_QNAME = new QName("http://service.unionbank.com/", "getcicRecord");
    private final static QName _GetGlForTerminal_QNAME = new QName("http://service.unionbank.com/", "getGlForTerminal");
    private final static QName _GetAccountSummaryByCustomerID_QNAME = new QName("http://service.unionbank.com/", "getAccountSummaryByCustomerID");
    private final static QName _GetActivityLogResponse_QNAME = new QName("http://service.unionbank.com/", "getActivityLogResponse");
    private final static QName _GetcicRecordResponse_QNAME = new QName("http://service.unionbank.com/", "getcicRecordResponse");
    private final static QName _GetActivityLog_QNAME = new QName("http://service.unionbank.com/", "getActivityLog");
    private final static QName _GetChequeNumberInquiry_QNAME = new QName("http://service.unionbank.com/", "getChequeNumberInquiry");
    private final static QName _GetAccountSummaryANDTransactionsResponse_QNAME = new QName("http://service.unionbank.com/", "getAccountSummaryANDTransactionsResponse");
    private final static QName _GetAccountSummaryByCustomerIDResponse_QNAME = new QName("http://service.unionbank.com/", "getAccountSummaryByCustomerIDResponse");
    private final static QName _GetloanScoreResponse_QNAME = new QName("http://service.unionbank.com/", "getloanScoreResponse");
    private final static QName _GetCustomerDetailsbyShortName_QNAME = new QName("http://service.unionbank.com/", "getCustomerDetailsbyShortName");
    private final static QName _GetMangersChequeStatusResponse_QNAME = new QName("http://service.unionbank.com/", "getMangersChequeStatusResponse");
    private final static QName _GetSingleTransactionDetailsResponse_QNAME = new QName("http://service.unionbank.com/", "getSingleTransactionDetailsResponse");
    private final static QName _GetChqUtilization_QNAME = new QName("http://service.unionbank.com/", "getChqUtilization");
    private final static QName _GetCustomerMandate_QNAME = new QName("http://service.unionbank.com/", "getCustomerMandate");
    private final static QName _GetAccountMandate_QNAME = new QName("http://service.unionbank.com/", "getAccountMandate");
    private final static QName _GetCustomerDetailsbyPhoneNumberResponse_QNAME = new QName("http://service.unionbank.com/", "getCustomerDetailsbyPhoneNumberResponse");
    private final static QName _GetMangersChequeStatusInfoResponse_QNAME = new QName("http://service.unionbank.com/", "getMangersChequeStatusInfoResponse");
    private final static QName _GetBranchList_QNAME = new QName("http://service.unionbank.com/", "getBranchList");
    private final static QName _GetGlForTerminalResponse_QNAME = new QName("http://service.unionbank.com/", "getGlForTerminalResponse");
    private final static QName _GetMangersChequeStatus_QNAME = new QName("http://service.unionbank.com/", "getMangersChequeStatus");
    private final static QName _NameEnquiry_QNAME = new QName("http://service.unionbank.com/", "NameEnquiry");
    private final static QName _GetActivityLogSpecific_QNAME = new QName("http://service.unionbank.com/", "getActivityLogSpecific");
    private final static QName _GetAccounMemoInfo_QNAME = new QName("http://service.unionbank.com/", "getAccounMemoInfo");
    private final static QName _GetBranchListResponse_QNAME = new QName("http://service.unionbank.com/", "getBranchListResponse");
    private final static QName _NameEnquiryResponse_QNAME = new QName("http://service.unionbank.com/", "NameEnquiryResponse");
    private final static QName _AccountSearchByAcctTitleAndBranchCode_QNAME = new QName("http://service.unionbank.com/", "AccountSearchByAcctTitleAndBranchCode");
    private final static QName _GetAccountSerachByAccountTitleResponse_QNAME = new QName("http://service.unionbank.com/", "getAccountSerachByAccountTitleResponse");
    private final static QName _CreateLog_QNAME = new QName("http://service.unionbank.com/", "createLog");
    private final static QName _GetProductListResponse_QNAME = new QName("http://service.unionbank.com/", "getProductListResponse");
    private final static QName _GetSingleTransactionDetails_QNAME = new QName("http://service.unionbank.com/", "getSingleTransactionDetails");
    private final static QName _GetCustomerDetailsWithAcctNumber_QNAME = new QName("http://service.unionbank.com/", "getCustomerDetailsWithAcctNumber");
    private final static QName _GetAccountSummary_QNAME = new QName("http://service.unionbank.com/", "getAccountSummary");
    private final static QName _GetCustomerAcctsDetail_QNAME = new QName("http://service.unionbank.com/", "getCustomerAcctsDetail");
    private final static QName _CreateLogResponse_QNAME = new QName("http://service.unionbank.com/", "createLogResponse");
    private final static QName _GetAccountSummaryANDTxnGenericResponse_QNAME = new QName("http://service.unionbank.com/", "getAccountSummaryANDTxnGenericResponse");
    private final static QName _GetAccountSummaryResponse_QNAME = new QName("http://service.unionbank.com/", "getAccountSummaryResponse");
    private final static QName _GetProductList_QNAME = new QName("http://service.unionbank.com/", "getProductList");
    private final static QName _GetActivityLogSpecificResponse_QNAME = new QName("http://service.unionbank.com/", "getActivityLogSpecificResponse");
    private final static QName _GetCustomerDetailsbyShortNameResponse_QNAME = new QName("http://service.unionbank.com/", "getCustomerDetailsbyShortNameResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.unionbankng.marcus.accountenquiry
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCustomerDetailsWithAcctNumber }
     * 
     */
    public GetCustomerDetailsWithAcctNumber createGetCustomerDetailsWithAcctNumber() {
        return new GetCustomerDetailsWithAcctNumber();
    }

    /**
     * Create an instance of {@link CreateLog }
     * 
     */
    public CreateLog createCreateLog() {
        return new CreateLog();
    }

    /**
     * Create an instance of {@link GetProductListResponse }
     * 
     */
    public GetProductListResponse createGetProductListResponse() {
        return new GetProductListResponse();
    }

    /**
     * Create an instance of {@link GetSingleTransactionDetails }
     * 
     */
    public GetSingleTransactionDetails createGetSingleTransactionDetails() {
        return new GetSingleTransactionDetails();
    }

    /**
     * Create an instance of {@link GetCustomerAcctsDetail }
     * 
     */
    public GetCustomerAcctsDetail createGetCustomerAcctsDetail() {
        return new GetCustomerAcctsDetail();
    }

    /**
     * Create an instance of {@link CreateLogResponse }
     * 
     */
    public CreateLogResponse createCreateLogResponse() {
        return new CreateLogResponse();
    }

    /**
     * Create an instance of {@link GetAccountSummary }
     * 
     */
    public GetAccountSummary createGetAccountSummary() {
        return new GetAccountSummary();
    }

    /**
     * Create an instance of {@link NameEnquiryResponse }
     * 
     */
    public NameEnquiryResponse createNameEnquiryResponse() {
        return new NameEnquiryResponse();
    }

    /**
     * Create an instance of {@link AccountSearchByAcctTitleAndBranchCode }
     * 
     */
    public AccountSearchByAcctTitleAndBranchCode createAccountSearchByAcctTitleAndBranchCode() {
        return new AccountSearchByAcctTitleAndBranchCode();
    }

    /**
     * Create an instance of {@link GetAccountSerachByAccountTitleResponse }
     * 
     */
    public GetAccountSerachByAccountTitleResponse createGetAccountSerachByAccountTitleResponse() {
        return new GetAccountSerachByAccountTitleResponse();
    }

    /**
     * Create an instance of {@link GetActivityLogSpecificResponse }
     * 
     */
    public GetActivityLogSpecificResponse createGetActivityLogSpecificResponse() {
        return new GetActivityLogSpecificResponse();
    }

    /**
     * Create an instance of {@link GetCustomerDetailsbyShortNameResponse }
     * 
     */
    public GetCustomerDetailsbyShortNameResponse createGetCustomerDetailsbyShortNameResponse() {
        return new GetCustomerDetailsbyShortNameResponse();
    }

    /**
     * Create an instance of {@link GetAccountSummaryANDTxnGenericResponse }
     * 
     */
    public GetAccountSummaryANDTxnGenericResponse createGetAccountSummaryANDTxnGenericResponse() {
        return new GetAccountSummaryANDTxnGenericResponse();
    }

    /**
     * Create an instance of {@link GetAccountSummaryResponse }
     * 
     */
    public GetAccountSummaryResponse createGetAccountSummaryResponse() {
        return new GetAccountSummaryResponse();
    }

    /**
     * Create an instance of {@link GetProductList }
     * 
     */
    public GetProductList createGetProductList() {
        return new GetProductList();
    }

    /**
     * Create an instance of {@link GetSingleTransactionDetailsResponse }
     * 
     */
    public GetSingleTransactionDetailsResponse createGetSingleTransactionDetailsResponse() {
        return new GetSingleTransactionDetailsResponse();
    }

    /**
     * Create an instance of {@link GetMangersChequeStatusResponse }
     * 
     */
    public GetMangersChequeStatusResponse createGetMangersChequeStatusResponse() {
        return new GetMangersChequeStatusResponse();
    }

    /**
     * Create an instance of {@link GetCustomerDetailsbyShortName }
     * 
     */
    public GetCustomerDetailsbyShortName createGetCustomerDetailsbyShortName() {
        return new GetCustomerDetailsbyShortName();
    }

    /**
     * Create an instance of {@link GetAccountSummaryANDTransactionsResponse }
     * 
     */
    public GetAccountSummaryANDTransactionsResponse createGetAccountSummaryANDTransactionsResponse() {
        return new GetAccountSummaryANDTransactionsResponse();
    }

    /**
     * Create an instance of {@link GetAccountSummaryByCustomerIDResponse }
     * 
     */
    public GetAccountSummaryByCustomerIDResponse createGetAccountSummaryByCustomerIDResponse() {
        return new GetAccountSummaryByCustomerIDResponse();
    }

    /**
     * Create an instance of {@link GetloanScoreResponse }
     * 
     */
    public GetloanScoreResponse createGetloanScoreResponse() {
        return new GetloanScoreResponse();
    }

    /**
     * Create an instance of {@link GetGlForTerminalResponse }
     * 
     */
    public GetGlForTerminalResponse createGetGlForTerminalResponse() {
        return new GetGlForTerminalResponse();
    }

    /**
     * Create an instance of {@link GetMangersChequeStatus }
     * 
     */
    public GetMangersChequeStatus createGetMangersChequeStatus() {
        return new GetMangersChequeStatus();
    }

    /**
     * Create an instance of {@link GetBranchList }
     * 
     */
    public GetBranchList createGetBranchList() {
        return new GetBranchList();
    }

    /**
     * Create an instance of {@link GetActivityLogSpecific }
     * 
     */
    public GetActivityLogSpecific createGetActivityLogSpecific() {
        return new GetActivityLogSpecific();
    }

    /**
     * Create an instance of {@link GetAccounMemoInfo }
     * 
     */
    public GetAccounMemoInfo createGetAccounMemoInfo() {
        return new GetAccounMemoInfo();
    }

    /**
     * Create an instance of {@link GetBranchListResponse }
     * 
     */
    public GetBranchListResponse createGetBranchListResponse() {
        return new GetBranchListResponse();
    }

    /**
     * Create an instance of {@link NameEnquiry }
     * 
     */
    public NameEnquiry createNameEnquiry() {
        return new NameEnquiry();
    }

    /**
     * Create an instance of {@link GetAccountMandate }
     * 
     */
    public GetAccountMandate createGetAccountMandate() {
        return new GetAccountMandate();
    }

    /**
     * Create an instance of {@link GetCustomerDetailsbyPhoneNumberResponse }
     * 
     */
    public GetCustomerDetailsbyPhoneNumberResponse createGetCustomerDetailsbyPhoneNumberResponse() {
        return new GetCustomerDetailsbyPhoneNumberResponse();
    }

    /**
     * Create an instance of {@link GetMangersChequeStatusInfoResponse }
     * 
     */
    public GetMangersChequeStatusInfoResponse createGetMangersChequeStatusInfoResponse() {
        return new GetMangersChequeStatusInfoResponse();
    }

    /**
     * Create an instance of {@link GetChqUtilization }
     * 
     */
    public GetChqUtilization createGetChqUtilization() {
        return new GetChqUtilization();
    }

    /**
     * Create an instance of {@link GetCustomerMandate }
     * 
     */
    public GetCustomerMandate createGetCustomerMandate() {
        return new GetCustomerMandate();
    }

    /**
     * Create an instance of {@link GetOverDraftListResponse }
     * 
     */
    public GetOverDraftListResponse createGetOverDraftListResponse() {
        return new GetOverDraftListResponse();
    }

    /**
     * Create an instance of {@link GetLoanList }
     * 
     */
    public GetLoanList createGetLoanList() {
        return new GetLoanList();
    }

    /**
     * Create an instance of {@link GetAccountSerachByAccountNumberResponse }
     * 
     */
    public GetAccountSerachByAccountNumberResponse createGetAccountSerachByAccountNumberResponse() {
        return new GetAccountSerachByAccountNumberResponse();
    }

    /**
     * Create an instance of {@link GetChqUtilizationResponse }
     * 
     */
    public GetChqUtilizationResponse createGetChqUtilizationResponse() {
        return new GetChqUtilizationResponse();
    }

    /**
     * Create an instance of {@link GetCustomerAcctsDetailResponse }
     * 
     */
    public GetCustomerAcctsDetailResponse createGetCustomerAcctsDetailResponse() {
        return new GetCustomerAcctsDetailResponse();
    }

    /**
     * Create an instance of {@link GetloanScore }
     * 
     */
    public GetloanScore createGetloanScore() {
        return new GetloanScore();
    }

    /**
     * Create an instance of {@link GetAccountSummaryANDTransactions }
     * 
     */
    public GetAccountSummaryANDTransactions createGetAccountSummaryANDTransactions() {
        return new GetAccountSummaryANDTransactions();
    }

    /**
     * Create an instance of {@link GetCustomerDetailsWithAcctNumberResponse }
     * 
     */
    public GetCustomerDetailsWithAcctNumberResponse createGetCustomerDetailsWithAcctNumberResponse() {
        return new GetCustomerDetailsWithAcctNumberResponse();
    }

    /**
     * Create an instance of {@link GetAccountSerachByAccountNumber }
     * 
     */
    public GetAccountSerachByAccountNumber createGetAccountSerachByAccountNumber() {
        return new GetAccountSerachByAccountNumber();
    }

    /**
     * Create an instance of {@link GetcicRecordResponse }
     * 
     */
    public GetcicRecordResponse createGetcicRecordResponse() {
        return new GetcicRecordResponse();
    }

    /**
     * Create an instance of {@link GetChequeNumberInquiry }
     * 
     */
    public GetChequeNumberInquiry createGetChequeNumberInquiry() {
        return new GetChequeNumberInquiry();
    }

    /**
     * Create an instance of {@link GetActivityLog }
     * 
     */
    public GetActivityLog createGetActivityLog() {
        return new GetActivityLog();
    }

    /**
     * Create an instance of {@link GetcicRecord }
     * 
     */
    public GetcicRecord createGetcicRecord() {
        return new GetcicRecord();
    }

    /**
     * Create an instance of {@link GetAccountSummaryByCustomerID }
     * 
     */
    public GetAccountSummaryByCustomerID createGetAccountSummaryByCustomerID() {
        return new GetAccountSummaryByCustomerID();
    }

    /**
     * Create an instance of {@link GetActivityLogResponse }
     * 
     */
    public GetActivityLogResponse createGetActivityLogResponse() {
        return new GetActivityLogResponse();
    }

    /**
     * Create an instance of {@link GetGlForTerminal }
     * 
     */
    public GetGlForTerminal createGetGlForTerminal() {
        return new GetGlForTerminal();
    }

    /**
     * Create an instance of {@link GetAccountMandateResponse }
     * 
     */
    public GetAccountMandateResponse createGetAccountMandateResponse() {
        return new GetAccountMandateResponse();
    }

    /**
     * Create an instance of {@link GetMangersChequeStatusInfo }
     * 
     */
    public GetMangersChequeStatusInfo createGetMangersChequeStatusInfo() {
        return new GetMangersChequeStatusInfo();
    }

    /**
     * Create an instance of {@link GetAccountSummaryANDTxnGeneric }
     * 
     */
    public GetAccountSummaryANDTxnGeneric createGetAccountSummaryANDTxnGeneric() {
        return new GetAccountSummaryANDTxnGeneric();
    }

    /**
     * Create an instance of {@link GetChequeNumberInquiryResponse }
     * 
     */
    public GetChequeNumberInquiryResponse createGetChequeNumberInquiryResponse() {
        return new GetChequeNumberInquiryResponse();
    }

    /**
     * Create an instance of {@link AccountSearchByAcctTitleAndBranchCodeResponse }
     * 
     */
    public AccountSearchByAcctTitleAndBranchCodeResponse createAccountSearchByAcctTitleAndBranchCodeResponse() {
        return new AccountSearchByAcctTitleAndBranchCodeResponse();
    }

    /**
     * Create an instance of {@link GetCustomerDetailsResponse }
     * 
     */
    public GetCustomerDetailsResponse createGetCustomerDetailsResponse() {
        return new GetCustomerDetailsResponse();
    }

    /**
     * Create an instance of {@link GetCustomerDetailswithFirstandLastResponse }
     * 
     */
    public GetCustomerDetailswithFirstandLastResponse createGetCustomerDetailswithFirstandLastResponse() {
        return new GetCustomerDetailswithFirstandLastResponse();
    }

    /**
     * Create an instance of {@link GetOverDraftList }
     * 
     */
    public GetOverDraftList createGetOverDraftList() {
        return new GetOverDraftList();
    }

    /**
     * Create an instance of {@link GetLoanListResponse }
     * 
     */
    public GetLoanListResponse createGetLoanListResponse() {
        return new GetLoanListResponse();
    }

    /**
     * Create an instance of {@link GetCustomerDetails }
     * 
     */
    public GetCustomerDetails createGetCustomerDetails() {
        return new GetCustomerDetails();
    }

    /**
     * Create an instance of {@link GetAccounMemoInfoResponse }
     * 
     */
    public GetAccounMemoInfoResponse createGetAccounMemoInfoResponse() {
        return new GetAccounMemoInfoResponse();
    }

    /**
     * Create an instance of {@link GetAccountSerachByAccountTitle }
     * 
     */
    public GetAccountSerachByAccountTitle createGetAccountSerachByAccountTitle() {
        return new GetAccountSerachByAccountTitle();
    }

    /**
     * Create an instance of {@link GetCustomerDetailswithFirstandLast }
     * 
     */
    public GetCustomerDetailswithFirstandLast createGetCustomerDetailswithFirstandLast() {
        return new GetCustomerDetailswithFirstandLast();
    }

    /**
     * Create an instance of {@link GetCustomerMandateResponse }
     * 
     */
    public GetCustomerMandateResponse createGetCustomerMandateResponse() {
        return new GetCustomerMandateResponse();
    }

    /**
     * Create an instance of {@link GetCustomerDetailsbyPhoneNumber }
     * 
     */
    public GetCustomerDetailsbyPhoneNumber createGetCustomerDetailsbyPhoneNumber() {
        return new GetCustomerDetailsbyPhoneNumber();
    }

    /**
     * Create an instance of {@link CustomerMandate }
     * 
     */
    public CustomerMandate createCustomerMandate() {
        return new CustomerMandate();
    }

    /**
     * Create an instance of {@link CustomerAcctsWithTransactionInfoWithBranch }
     * 
     */
    public CustomerAcctsWithTransactionInfoWithBranch createCustomerAcctsWithTransactionInfoWithBranch() {
        return new CustomerAcctsWithTransactionInfoWithBranch();
    }

    /**
     * Create an instance of {@link CustomerAccts }
     * 
     */
    public CustomerAccts createCustomerAccts() {
        return new CustomerAccts();
    }

    /**
     * Create an instance of {@link AccountMandate }
     * 
     */
    public AccountMandate createAccountMandate() {
        return new AccountMandate();
    }

    /**
     * Create an instance of {@link ClientDetails }
     * 
     */
    public ClientDetails createClientDetails() {
        return new ClientDetails();
    }

    /**
     * Create an instance of {@link CustomerAcctsWithTranInfoWithBranchGeneric }
     * 
     */
    public CustomerAcctsWithTranInfoWithBranchGeneric createCustomerAcctsWithTranInfoWithBranchGeneric() {
        return new CustomerAcctsWithTranInfoWithBranchGeneric();
    }

    /**
     * Create an instance of {@link AccountSearchInfo }
     * 
     */
    public AccountSearchInfo createAccountSearchInfo() {
        return new AccountSearchInfo();
    }

    /**
     * Create an instance of {@link AccountSearchByAcctTitle }
     * 
     */
    public AccountSearchByAcctTitle createAccountSearchByAcctTitle() {
        return new AccountSearchByAcctTitle();
    }

    /**
     * Create an instance of {@link AccountMemo }
     * 
     */
    public AccountMemo createAccountMemo() {
        return new AccountMemo();
    }

    /**
     * Create an instance of {@link CustomerAddress }
     * 
     */
    public CustomerAddress createCustomerAddress() {
        return new CustomerAddress();
    }

    /**
     * Create an instance of {@link LoanList }
     * 
     */
    public LoanList createLoanList() {
        return new LoanList();
    }

    /**
     * Create an instance of {@link TranDetailsWithBranch }
     * 
     */
    public TranDetailsWithBranch createTranDetailsWithBranch() {
        return new TranDetailsWithBranch();
    }

    /**
     * Create an instance of {@link LoanDetailsResponse }
     * 
     */
    public LoanDetailsResponse createLoanDetailsResponse() {
        return new LoanDetailsResponse();
    }

    /**
     * Create an instance of {@link SingleTransactionDetails }
     * 
     */
    public SingleTransactionDetails createSingleTransactionDetails() {
        return new SingleTransactionDetails();
    }

    /**
     * Create an instance of {@link GlTerminalDetails }
     * 
     */
    public GlTerminalDetails createGlTerminalDetails() {
        return new GlTerminalDetails();
    }

    /**
     * Create an instance of {@link ProductList }
     * 
     */
    public ProductList createProductList() {
        return new ProductList();
    }

    /**
     * Create an instance of {@link CustomerAcctsWithTransactionsWithBranch }
     * 
     */
    public CustomerAcctsWithTransactionsWithBranch createCustomerAcctsWithTransactionsWithBranch() {
        return new CustomerAcctsWithTransactionsWithBranch();
    }

    /**
     * Create an instance of {@link BranchList }
     * 
     */
    public BranchList createBranchList() {
        return new BranchList();
    }

    /**
     * Create an instance of {@link CustomerNames }
     * 
     */
    public CustomerNames createCustomerNames() {
        return new CustomerNames();
    }

    /**
     * Create an instance of {@link CustomerAcctsWithTransactionsGeneric }
     * 
     */
    public CustomerAcctsWithTransactionsGeneric createCustomerAcctsWithTransactionsGeneric() {
        return new CustomerAcctsWithTransactionsGeneric();
    }

    /**
     * Create an instance of {@link AccountMemoInfo }
     * 
     */
    public AccountMemoInfo createAccountMemoInfo() {
        return new AccountMemoInfo();
    }

    /**
     * Create an instance of {@link NameEnquiryResponses }
     * 
     */
    public NameEnquiryResponses createNameEnquiryResponses() {
        return new NameEnquiryResponses();
    }

    /**
     * Create an instance of {@link CustomerAcctsInfo }
     * 
     */
    public CustomerAcctsInfo createCustomerAcctsInfo() {
        return new CustomerAcctsInfo();
    }

    /**
     * Create an instance of {@link TranDetailsGeneric }
     * 
     */
    public TranDetailsGeneric createTranDetailsGeneric() {
        return new TranDetailsGeneric();
    }

    /**
     * Create an instance of {@link ManagersChequeDetails }
     * 
     */
    public ManagersChequeDetails createManagersChequeDetails() {
        return new ManagersChequeDetails();
    }

    /**
     * Create an instance of {@link OverdraftList }
     * 
     */
    public OverdraftList createOverdraftList() {
        return new OverdraftList();
    }

    /**
     * Create an instance of {@link CustomerAcctsDetail }
     * 
     */
    public CustomerAcctsDetail createCustomerAcctsDetail() {
        return new CustomerAcctsDetail();
    }

    /**
     * Create an instance of {@link AuditInformation }
     * 
     */
    public AuditInformation createAuditInformation() {
        return new AuditInformation();
    }

    /**
     * Create an instance of {@link UBNHeaderType }
     * 
     */
    public UBNHeaderType createUBNHeaderType() {
        return new UBNHeaderType();
    }

    /**
     * Create an instance of {@link BusinessKeyPairs }
     * 
     */
    public BusinessKeyPairs createBusinessKeyPairs() {
        return new BusinessKeyPairs();
    }

    /**
     * Create an instance of {@link BusinessKeyType }
     * 
     */
    public BusinessKeyType createBusinessKeyType() {
        return new BusinessKeyType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChequeNumberInquiryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getChequeNumberInquiryResponse")
    public JAXBElement<GetChequeNumberInquiryResponse> createGetChequeNumberInquiryResponse(GetChequeNumberInquiryResponse value) {
        return new JAXBElement<GetChequeNumberInquiryResponse>(_GetChequeNumberInquiryResponse_QNAME, GetChequeNumberInquiryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSummaryANDTxnGeneric }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSummaryANDTxnGeneric")
    public JAXBElement<GetAccountSummaryANDTxnGeneric> createGetAccountSummaryANDTxnGeneric(GetAccountSummaryANDTxnGeneric value) {
        return new JAXBElement<GetAccountSummaryANDTxnGeneric>(_GetAccountSummaryANDTxnGeneric_QNAME, GetAccountSummaryANDTxnGeneric.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountSearchByAcctTitleAndBranchCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "AccountSearchByAcctTitleAndBranchCodeResponse")
    public JAXBElement<AccountSearchByAcctTitleAndBranchCodeResponse> createAccountSearchByAcctTitleAndBranchCodeResponse(AccountSearchByAcctTitleAndBranchCodeResponse value) {
        return new JAXBElement<AccountSearchByAcctTitleAndBranchCodeResponse>(_AccountSearchByAcctTitleAndBranchCodeResponse_QNAME, AccountSearchByAcctTitleAndBranchCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMangersChequeStatusInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getMangersChequeStatusInfo")
    public JAXBElement<GetMangersChequeStatusInfo> createGetMangersChequeStatusInfo(GetMangersChequeStatusInfo value) {
        return new JAXBElement<GetMangersChequeStatusInfo>(_GetMangersChequeStatusInfo_QNAME, GetMangersChequeStatusInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountMandateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountMandateResponse")
    public JAXBElement<GetAccountMandateResponse> createGetAccountMandateResponse(GetAccountMandateResponse value) {
        return new JAXBElement<GetAccountMandateResponse>(_GetAccountMandateResponse_QNAME, GetAccountMandateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UBNHeaderType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.unionbank.com.ng/utilities/Header/v1_0/UBNHeader", name = "UBNHeader")
    public JAXBElement<UBNHeaderType> createUBNHeader(UBNHeaderType value) {
        return new JAXBElement<UBNHeaderType>(_UBNHeader_QNAME, UBNHeaderType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccounMemoInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccounMemoInfoResponse")
    public JAXBElement<GetAccounMemoInfoResponse> createGetAccounMemoInfoResponse(GetAccounMemoInfoResponse value) {
        return new JAXBElement<GetAccounMemoInfoResponse>(_GetAccounMemoInfoResponse_QNAME, GetAccounMemoInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSerachByAccountTitle }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSerachByAccountTitle")
    public JAXBElement<GetAccountSerachByAccountTitle> createGetAccountSerachByAccountTitle(GetAccountSerachByAccountTitle value) {
        return new JAXBElement<GetAccountSerachByAccountTitle>(_GetAccountSerachByAccountTitle_QNAME, GetAccountSerachByAccountTitle.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerDetailswithFirstandLast }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerDetailswithFirstandLast")
    public JAXBElement<GetCustomerDetailswithFirstandLast> createGetCustomerDetailswithFirstandLast(GetCustomerDetailswithFirstandLast value) {
        return new JAXBElement<GetCustomerDetailswithFirstandLast>(_GetCustomerDetailswithFirstandLast_QNAME, GetCustomerDetailswithFirstandLast.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerDetailsbyPhoneNumber }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerDetailsbyPhoneNumber")
    public JAXBElement<GetCustomerDetailsbyPhoneNumber> createGetCustomerDetailsbyPhoneNumber(GetCustomerDetailsbyPhoneNumber value) {
        return new JAXBElement<GetCustomerDetailsbyPhoneNumber>(_GetCustomerDetailsbyPhoneNumber_QNAME, GetCustomerDetailsbyPhoneNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerMandateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerMandateResponse")
    public JAXBElement<GetCustomerMandateResponse> createGetCustomerMandateResponse(GetCustomerMandateResponse value) {
        return new JAXBElement<GetCustomerMandateResponse>(_GetCustomerMandateResponse_QNAME, GetCustomerMandateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLoanListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getLoanListResponse")
    public JAXBElement<GetLoanListResponse> createGetLoanListResponse(GetLoanListResponse value) {
        return new JAXBElement<GetLoanListResponse>(_GetLoanListResponse_QNAME, GetLoanListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerDetailsResponse")
    public JAXBElement<GetCustomerDetailsResponse> createGetCustomerDetailsResponse(GetCustomerDetailsResponse value) {
        return new JAXBElement<GetCustomerDetailsResponse>(_GetCustomerDetailsResponse_QNAME, GetCustomerDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerDetailswithFirstandLastResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerDetailswithFirstandLastResponse")
    public JAXBElement<GetCustomerDetailswithFirstandLastResponse> createGetCustomerDetailswithFirstandLastResponse(GetCustomerDetailswithFirstandLastResponse value) {
        return new JAXBElement<GetCustomerDetailswithFirstandLastResponse>(_GetCustomerDetailswithFirstandLastResponse_QNAME, GetCustomerDetailswithFirstandLastResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOverDraftList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getOverDraftList")
    public JAXBElement<GetOverDraftList> createGetOverDraftList(GetOverDraftList value) {
        return new JAXBElement<GetOverDraftList>(_GetOverDraftList_QNAME, GetOverDraftList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerDetails")
    public JAXBElement<GetCustomerDetails> createGetCustomerDetails(GetCustomerDetails value) {
        return new JAXBElement<GetCustomerDetails>(_GetCustomerDetails_QNAME, GetCustomerDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSerachByAccountNumber }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSerachByAccountNumber")
    public JAXBElement<GetAccountSerachByAccountNumber> createGetAccountSerachByAccountNumber(GetAccountSerachByAccountNumber value) {
        return new JAXBElement<GetAccountSerachByAccountNumber>(_GetAccountSerachByAccountNumber_QNAME, GetAccountSerachByAccountNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerDetailsWithAcctNumberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerDetailsWithAcctNumberResponse")
    public JAXBElement<GetCustomerDetailsWithAcctNumberResponse> createGetCustomerDetailsWithAcctNumberResponse(GetCustomerDetailsWithAcctNumberResponse value) {
        return new JAXBElement<GetCustomerDetailsWithAcctNumberResponse>(_GetCustomerDetailsWithAcctNumberResponse_QNAME, GetCustomerDetailsWithAcctNumberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSerachByAccountNumberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSerachByAccountNumberResponse")
    public JAXBElement<GetAccountSerachByAccountNumberResponse> createGetAccountSerachByAccountNumberResponse(GetAccountSerachByAccountNumberResponse value) {
        return new JAXBElement<GetAccountSerachByAccountNumberResponse>(_GetAccountSerachByAccountNumberResponse_QNAME, GetAccountSerachByAccountNumberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChqUtilizationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getChqUtilizationResponse")
    public JAXBElement<GetChqUtilizationResponse> createGetChqUtilizationResponse(GetChqUtilizationResponse value) {
        return new JAXBElement<GetChqUtilizationResponse>(_GetChqUtilizationResponse_QNAME, GetChqUtilizationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerAcctsDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerAcctsDetailResponse")
    public JAXBElement<GetCustomerAcctsDetailResponse> createGetCustomerAcctsDetailResponse(GetCustomerAcctsDetailResponse value) {
        return new JAXBElement<GetCustomerAcctsDetailResponse>(_GetCustomerAcctsDetailResponse_QNAME, GetCustomerAcctsDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetloanScore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getloanScore")
    public JAXBElement<GetloanScore> createGetloanScore(GetloanScore value) {
        return new JAXBElement<GetloanScore>(_GetloanScore_QNAME, GetloanScore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOverDraftListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getOverDraftListResponse")
    public JAXBElement<GetOverDraftListResponse> createGetOverDraftListResponse(GetOverDraftListResponse value) {
        return new JAXBElement<GetOverDraftListResponse>(_GetOverDraftListResponse_QNAME, GetOverDraftListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLoanList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getLoanList")
    public JAXBElement<GetLoanList> createGetLoanList(GetLoanList value) {
        return new JAXBElement<GetLoanList>(_GetLoanList_QNAME, GetLoanList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSummaryANDTransactions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSummaryANDTransactions")
    public JAXBElement<GetAccountSummaryANDTransactions> createGetAccountSummaryANDTransactions(GetAccountSummaryANDTransactions value) {
        return new JAXBElement<GetAccountSummaryANDTransactions>(_GetAccountSummaryANDTransactions_QNAME, GetAccountSummaryANDTransactions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetcicRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getcicRecord")
    public JAXBElement<GetcicRecord> createGetcicRecord(GetcicRecord value) {
        return new JAXBElement<GetcicRecord>(_GetcicRecord_QNAME, GetcicRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGlForTerminal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getGlForTerminal")
    public JAXBElement<GetGlForTerminal> createGetGlForTerminal(GetGlForTerminal value) {
        return new JAXBElement<GetGlForTerminal>(_GetGlForTerminal_QNAME, GetGlForTerminal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSummaryByCustomerID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSummaryByCustomerID")
    public JAXBElement<GetAccountSummaryByCustomerID> createGetAccountSummaryByCustomerID(GetAccountSummaryByCustomerID value) {
        return new JAXBElement<GetAccountSummaryByCustomerID>(_GetAccountSummaryByCustomerID_QNAME, GetAccountSummaryByCustomerID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getActivityLogResponse")
    public JAXBElement<GetActivityLogResponse> createGetActivityLogResponse(GetActivityLogResponse value) {
        return new JAXBElement<GetActivityLogResponse>(_GetActivityLogResponse_QNAME, GetActivityLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetcicRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getcicRecordResponse")
    public JAXBElement<GetcicRecordResponse> createGetcicRecordResponse(GetcicRecordResponse value) {
        return new JAXBElement<GetcicRecordResponse>(_GetcicRecordResponse_QNAME, GetcicRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getActivityLog")
    public JAXBElement<GetActivityLog> createGetActivityLog(GetActivityLog value) {
        return new JAXBElement<GetActivityLog>(_GetActivityLog_QNAME, GetActivityLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChequeNumberInquiry }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getChequeNumberInquiry")
    public JAXBElement<GetChequeNumberInquiry> createGetChequeNumberInquiry(GetChequeNumberInquiry value) {
        return new JAXBElement<GetChequeNumberInquiry>(_GetChequeNumberInquiry_QNAME, GetChequeNumberInquiry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSummaryANDTransactionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSummaryANDTransactionsResponse")
    public JAXBElement<GetAccountSummaryANDTransactionsResponse> createGetAccountSummaryANDTransactionsResponse(GetAccountSummaryANDTransactionsResponse value) {
        return new JAXBElement<GetAccountSummaryANDTransactionsResponse>(_GetAccountSummaryANDTransactionsResponse_QNAME, GetAccountSummaryANDTransactionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSummaryByCustomerIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSummaryByCustomerIDResponse")
    public JAXBElement<GetAccountSummaryByCustomerIDResponse> createGetAccountSummaryByCustomerIDResponse(GetAccountSummaryByCustomerIDResponse value) {
        return new JAXBElement<GetAccountSummaryByCustomerIDResponse>(_GetAccountSummaryByCustomerIDResponse_QNAME, GetAccountSummaryByCustomerIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetloanScoreResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getloanScoreResponse")
    public JAXBElement<GetloanScoreResponse> createGetloanScoreResponse(GetloanScoreResponse value) {
        return new JAXBElement<GetloanScoreResponse>(_GetloanScoreResponse_QNAME, GetloanScoreResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerDetailsbyShortName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerDetailsbyShortName")
    public JAXBElement<GetCustomerDetailsbyShortName> createGetCustomerDetailsbyShortName(GetCustomerDetailsbyShortName value) {
        return new JAXBElement<GetCustomerDetailsbyShortName>(_GetCustomerDetailsbyShortName_QNAME, GetCustomerDetailsbyShortName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMangersChequeStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getMangersChequeStatusResponse")
    public JAXBElement<GetMangersChequeStatusResponse> createGetMangersChequeStatusResponse(GetMangersChequeStatusResponse value) {
        return new JAXBElement<GetMangersChequeStatusResponse>(_GetMangersChequeStatusResponse_QNAME, GetMangersChequeStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSingleTransactionDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getSingleTransactionDetailsResponse")
    public JAXBElement<GetSingleTransactionDetailsResponse> createGetSingleTransactionDetailsResponse(GetSingleTransactionDetailsResponse value) {
        return new JAXBElement<GetSingleTransactionDetailsResponse>(_GetSingleTransactionDetailsResponse_QNAME, GetSingleTransactionDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChqUtilization }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getChqUtilization")
    public JAXBElement<GetChqUtilization> createGetChqUtilization(GetChqUtilization value) {
        return new JAXBElement<GetChqUtilization>(_GetChqUtilization_QNAME, GetChqUtilization.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerMandate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerMandate")
    public JAXBElement<GetCustomerMandate> createGetCustomerMandate(GetCustomerMandate value) {
        return new JAXBElement<GetCustomerMandate>(_GetCustomerMandate_QNAME, GetCustomerMandate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountMandate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountMandate")
    public JAXBElement<GetAccountMandate> createGetAccountMandate(GetAccountMandate value) {
        return new JAXBElement<GetAccountMandate>(_GetAccountMandate_QNAME, GetAccountMandate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerDetailsbyPhoneNumberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerDetailsbyPhoneNumberResponse")
    public JAXBElement<GetCustomerDetailsbyPhoneNumberResponse> createGetCustomerDetailsbyPhoneNumberResponse(GetCustomerDetailsbyPhoneNumberResponse value) {
        return new JAXBElement<GetCustomerDetailsbyPhoneNumberResponse>(_GetCustomerDetailsbyPhoneNumberResponse_QNAME, GetCustomerDetailsbyPhoneNumberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMangersChequeStatusInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getMangersChequeStatusInfoResponse")
    public JAXBElement<GetMangersChequeStatusInfoResponse> createGetMangersChequeStatusInfoResponse(GetMangersChequeStatusInfoResponse value) {
        return new JAXBElement<GetMangersChequeStatusInfoResponse>(_GetMangersChequeStatusInfoResponse_QNAME, GetMangersChequeStatusInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBranchList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getBranchList")
    public JAXBElement<GetBranchList> createGetBranchList(GetBranchList value) {
        return new JAXBElement<GetBranchList>(_GetBranchList_QNAME, GetBranchList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGlForTerminalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getGlForTerminalResponse")
    public JAXBElement<GetGlForTerminalResponse> createGetGlForTerminalResponse(GetGlForTerminalResponse value) {
        return new JAXBElement<GetGlForTerminalResponse>(_GetGlForTerminalResponse_QNAME, GetGlForTerminalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMangersChequeStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getMangersChequeStatus")
    public JAXBElement<GetMangersChequeStatus> createGetMangersChequeStatus(GetMangersChequeStatus value) {
        return new JAXBElement<GetMangersChequeStatus>(_GetMangersChequeStatus_QNAME, GetMangersChequeStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameEnquiry }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "NameEnquiry")
    public JAXBElement<NameEnquiry> createNameEnquiry(NameEnquiry value) {
        return new JAXBElement<NameEnquiry>(_NameEnquiry_QNAME, NameEnquiry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityLogSpecific }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getActivityLogSpecific")
    public JAXBElement<GetActivityLogSpecific> createGetActivityLogSpecific(GetActivityLogSpecific value) {
        return new JAXBElement<GetActivityLogSpecific>(_GetActivityLogSpecific_QNAME, GetActivityLogSpecific.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccounMemoInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccounMemoInfo")
    public JAXBElement<GetAccounMemoInfo> createGetAccounMemoInfo(GetAccounMemoInfo value) {
        return new JAXBElement<GetAccounMemoInfo>(_GetAccounMemoInfo_QNAME, GetAccounMemoInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBranchListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getBranchListResponse")
    public JAXBElement<GetBranchListResponse> createGetBranchListResponse(GetBranchListResponse value) {
        return new JAXBElement<GetBranchListResponse>(_GetBranchListResponse_QNAME, GetBranchListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameEnquiryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "NameEnquiryResponse")
    public JAXBElement<NameEnquiryResponse> createNameEnquiryResponse(NameEnquiryResponse value) {
        return new JAXBElement<NameEnquiryResponse>(_NameEnquiryResponse_QNAME, NameEnquiryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountSearchByAcctTitleAndBranchCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "AccountSearchByAcctTitleAndBranchCode")
    public JAXBElement<AccountSearchByAcctTitleAndBranchCode> createAccountSearchByAcctTitleAndBranchCode(AccountSearchByAcctTitleAndBranchCode value) {
        return new JAXBElement<AccountSearchByAcctTitleAndBranchCode>(_AccountSearchByAcctTitleAndBranchCode_QNAME, AccountSearchByAcctTitleAndBranchCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSerachByAccountTitleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSerachByAccountTitleResponse")
    public JAXBElement<GetAccountSerachByAccountTitleResponse> createGetAccountSerachByAccountTitleResponse(GetAccountSerachByAccountTitleResponse value) {
        return new JAXBElement<GetAccountSerachByAccountTitleResponse>(_GetAccountSerachByAccountTitleResponse_QNAME, GetAccountSerachByAccountTitleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "createLog")
    public JAXBElement<CreateLog> createCreateLog(CreateLog value) {
        return new JAXBElement<CreateLog>(_CreateLog_QNAME, CreateLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProductListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getProductListResponse")
    public JAXBElement<GetProductListResponse> createGetProductListResponse(GetProductListResponse value) {
        return new JAXBElement<GetProductListResponse>(_GetProductListResponse_QNAME, GetProductListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSingleTransactionDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getSingleTransactionDetails")
    public JAXBElement<GetSingleTransactionDetails> createGetSingleTransactionDetails(GetSingleTransactionDetails value) {
        return new JAXBElement<GetSingleTransactionDetails>(_GetSingleTransactionDetails_QNAME, GetSingleTransactionDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerDetailsWithAcctNumber }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerDetailsWithAcctNumber")
    public JAXBElement<GetCustomerDetailsWithAcctNumber> createGetCustomerDetailsWithAcctNumber(GetCustomerDetailsWithAcctNumber value) {
        return new JAXBElement<GetCustomerDetailsWithAcctNumber>(_GetCustomerDetailsWithAcctNumber_QNAME, GetCustomerDetailsWithAcctNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSummary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSummary")
    public JAXBElement<GetAccountSummary> createGetAccountSummary(GetAccountSummary value) {
        return new JAXBElement<GetAccountSummary>(_GetAccountSummary_QNAME, GetAccountSummary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerAcctsDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerAcctsDetail")
    public JAXBElement<GetCustomerAcctsDetail> createGetCustomerAcctsDetail(GetCustomerAcctsDetail value) {
        return new JAXBElement<GetCustomerAcctsDetail>(_GetCustomerAcctsDetail_QNAME, GetCustomerAcctsDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "createLogResponse")
    public JAXBElement<CreateLogResponse> createCreateLogResponse(CreateLogResponse value) {
        return new JAXBElement<CreateLogResponse>(_CreateLogResponse_QNAME, CreateLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSummaryANDTxnGenericResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSummaryANDTxnGenericResponse")
    public JAXBElement<GetAccountSummaryANDTxnGenericResponse> createGetAccountSummaryANDTxnGenericResponse(GetAccountSummaryANDTxnGenericResponse value) {
        return new JAXBElement<GetAccountSummaryANDTxnGenericResponse>(_GetAccountSummaryANDTxnGenericResponse_QNAME, GetAccountSummaryANDTxnGenericResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSummaryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getAccountSummaryResponse")
    public JAXBElement<GetAccountSummaryResponse> createGetAccountSummaryResponse(GetAccountSummaryResponse value) {
        return new JAXBElement<GetAccountSummaryResponse>(_GetAccountSummaryResponse_QNAME, GetAccountSummaryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProductList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getProductList")
    public JAXBElement<GetProductList> createGetProductList(GetProductList value) {
        return new JAXBElement<GetProductList>(_GetProductList_QNAME, GetProductList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityLogSpecificResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getActivityLogSpecificResponse")
    public JAXBElement<GetActivityLogSpecificResponse> createGetActivityLogSpecificResponse(GetActivityLogSpecificResponse value) {
        return new JAXBElement<GetActivityLogSpecificResponse>(_GetActivityLogSpecificResponse_QNAME, GetActivityLogSpecificResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerDetailsbyShortNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.unionbank.com/", name = "getCustomerDetailsbyShortNameResponse")
    public JAXBElement<GetCustomerDetailsbyShortNameResponse> createGetCustomerDetailsbyShortNameResponse(GetCustomerDetailsbyShortNameResponse value) {
        return new JAXBElement<GetCustomerDetailsbyShortNameResponse>(_GetCustomerDetailsbyShortNameResponse_QNAME, GetCustomerDetailsbyShortNameResponse.class, null, value);
    }

}
