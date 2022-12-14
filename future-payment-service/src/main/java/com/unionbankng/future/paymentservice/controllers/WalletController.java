package com.unionbankng.future.paymentservice.controllers;

import com.unionbankng.future.paymentservice.pojos.*;
import com.unionbankng.future.paymentservice.services.WalletService;
import com.unionbankng.future.paymentservice.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final App app;


    @PostMapping("/initiate-wallet-funding")
    public ApiResponse<?> initiateWalletFunding(@RequestBody InitiateFundingRequest request)  {
        app.print("Initiating wallet funding : ");
        app.print(request);
        return walletService.initiateWalletFunding(request);
    }


    @PostMapping("/verify-interswitch-transaction/{transactionRef}")
    public ApiResponse<WalletGenericResponse> handleInterswitchVerifyTransaction(@Valid @PathVariable String transactionRef, @RequestBody InterswitchSDKResponse request)  {
        app.print("Interswitch Verify Transaction : " + transactionRef);
        request.setTxnref(transactionRef);
        return walletService.verifyTransaction(request);
    }

    @PostMapping("/verify-paystack-transaction")
    public ApiResponse<WalletGenericResponse> handlePaystackVerifyTransaction(@Valid  @RequestBody PaystackSDKResponse request, OAuth2Authentication details)  {
        app.print("Paystack Verify Transaction : " + request.getTransactionReference());
        return walletService.verifyTransaction(request, details);
    }

    @PostMapping("/debit-wallet")
    public WalletGenericResponse instantGlTransferDebitWallet(@RequestBody WalletDebitRequest debitWalletRequest) {
        return walletService.debitWallet(debitWalletRequest);
    }


    @GetMapping("/resolve-bank-account")
	public ApiResponse validateBankAccount(@RequestParam("accountNo") String accountNo,
                                           @RequestParam("bankCode") String bankCode)  {
        app.print("Validating Account details from Wallet Service");
		return walletService.validateBankAccount(accountNo, bankCode);
	}


    @GetMapping("/get_balance")
    public WalletGenericResponse getWalletBalance(@RequestParam String walletId, @RequestParam String currencyCode) {
        return walletService.getWalletBalance(walletId, currencyCode);
    }


    @GetMapping("/wallet-logs/{walletId}")
    public ApiResponse<List<XLog>> findByWalletId(@PathVariable String walletId) {
        return walletService.fetchLogsByWalletId(walletId);
    }


    @GetMapping("/interswitch-bank-list")
	public ApiResponse<InterswitchBankResponse> getBankList()  {
		return walletService.getBankList();
	}
}
