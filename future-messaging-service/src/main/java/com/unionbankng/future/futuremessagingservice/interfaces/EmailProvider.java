package com.unionbankng.future.futuremessagingservice.interfaces;

import com.unionbankng.future.futuremessagingservice.pojo.EmailBody;
import com.unionbankng.future.futuremessagingservice.pojo.SMS;
import org.thymeleaf.TemplateEngine;

public interface EmailProvider {

    void init();
    void send(EmailBody email) throws Exception;
}
