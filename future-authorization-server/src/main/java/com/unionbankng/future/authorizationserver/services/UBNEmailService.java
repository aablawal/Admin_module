package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.EmailBody;
import com.unionbankng.future.authorizationserver.pojos.UbnEmailResponse;
import com.unionbankng.future.authorizationserver.retrofitservices.UBNEmailServiceInterface;
import com.unionbankng.future.authorizationserver.utils.App;
import com.unionbankng.future.authorizationserver.utils.UnsafeOkHttpClient;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.util.Calendar;

@RefreshScope
@Service
@RequiredArgsConstructor
public class UBNEmailService {

    private UBNEmailServiceInterface ubnEmailServiceInterface;
    private final UBNAuthService ubnAuthService;
    private final App app;
    @Value("${unionbankng.base.url}")
    private String ubnBaseURL;
    private final MessageSource messageSource;
    private final TemplateEngine templateEngine;


    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ubnBaseURL)
                .build();
        ubnEmailServiceInterface = retrofit.create(UBNEmailServiceInterface.class);
    }

    public APIResponse sendEmail(EmailBody body) {
        try {
            app.print("Sending Email...");
            String authorization = String.format("Bearer %s", ubnAuthService.getUBNAuthServerToken().getAccess_token());
            // app.print(authorization);
            Response<UbnEmailResponse> responseCall = ubnEmailServiceInterface.sendEmail(authorization, processEmailTemplate(body)).execute();
            UbnEmailResponse response = responseCall.body();
            app.print("Email Response StatusCode: " + responseCall.code());
//            app.print(response);

            return new APIResponse(responseCall.message(), responseCall.isSuccessful(), response);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(ex.getMessage(), false, null);
        }
    }
    private EmailBody processEmailTemplate(EmailBody emailBody){

        try {
            Context ctx = new Context(LocaleContextHolder.getLocale());
            if (emailBody.getRecipients().size() == 1) {
                ctx.setVariable("footname", emailBody.getRecipients().get(0).getDisplayName());
                ctx.setVariable("year", Calendar.getInstance().get(Calendar.YEAR));
            } else {
                ctx.setVariable("name", emailBody.getSubject());
            }

            ctx.setVariable("body", emailBody.getBody());

            final String htmlContent = templateEngine.process("mail/html/system-email.html", ctx);
            emailBody.setBody(htmlContent);

            return emailBody;
        }catch (Exception ex){
            System.out.println("Unable to generate email template");
            ex.printStackTrace();
            return null;
        }
    }
}
