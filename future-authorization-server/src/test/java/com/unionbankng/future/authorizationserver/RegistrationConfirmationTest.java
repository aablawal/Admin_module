package com.unionbankng.future.authorizationserver;

import com.unionbankng.future.authorizationserver.services.MemcachedHelperService;
import com.unionbankng.future.authorizationserver.services.UserConfirmationTokenService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

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
        boolean confirmed = userConfirmationTokenService.confirmUserAccountByToken("12233334444").getSuccess();
        Assert.assertEquals(false,confirmed);
    }


    @Test
    public void successTokenTest() {
        Mockito.when(memcachedHelperService.getValueByKey("12233334444")).thenReturn("abc@gmail.com");
        boolean confirmed = userConfirmationTokenService.confirmUserAccountByToken("12233334444").getSuccess();

        Assert.assertEquals(Boolean.TRUE,confirmed);

    }
}
