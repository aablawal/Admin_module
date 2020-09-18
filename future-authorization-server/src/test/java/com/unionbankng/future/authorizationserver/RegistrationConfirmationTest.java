package com.unionbankng.future.authorizationserver;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.UserType;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.services.MemcachedHelperService;
import com.unionbankng.future.authorizationserver.services.UserConfirmationTokenService;
import com.unionbankng.future.authorizationserver.services.UserService;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class RegistrationConfirmationTest extends AbstractTest {
    @Autowired
    private UserConfirmationTokenService userConfirmationTokenService;

    @MockBean
    MemcachedHelperService memcachedHelperService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void tokenDoesNotExist()  {

        Mockito.when(memcachedHelperService.getValueByKey("12233334444")).thenReturn(null);

        boolean confirmed = userConfirmationTokenService.confirmUserAccountByToken("12233334444");
        Assert.assertEquals(false,confirmed);

    }


    @Test
    public void successTokenTest() {

        Mockito.when(memcachedHelperService.getValueByKey("12233334444")).thenReturn("abc@gmail.com");
        boolean confirmed = userConfirmationTokenService.confirmUserAccountByToken("12233334444");

        Assert.assertEquals(Boolean.TRUE,confirmed);

    }
}
