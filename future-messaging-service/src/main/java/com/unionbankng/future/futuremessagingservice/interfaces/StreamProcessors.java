package com.unionbankng.future.futuremessagingservice.interfaces;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface StreamProcessors {

    @Input("email")
    SubscribableChannel emailInput();

    @Input("sms")
    SubscribableChannel smsInput();

    @Input("notification")
    SubscribableChannel notificationInput();

}
