package com.unionbankng.future.authorizationserver;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.entities.UserConfirmationToken;
import com.unionbankng.future.authorizationserver.enums.UserType;
import com.unionbankng.future.authorizationserver.repositories.UserConfirmationTokenRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.services.UserConfirmationTokenService;
import com.unionbankng.future.authorizationserver.services.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class RegistrationConfirmationTest extends AbstractTest {
    @Autowired
    private UserConfirmationTokenService userConfirmationTokenService;

    @Autowired
    private UserConfirmationTokenRepository userConfirmationTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void tokenDoesNotExist()  {

        int result = userConfirmationTokenService.confirmUserAccountByToken("12233334444");
        Assert.assertEquals(0,result);

    }


    @Test
    public void expiredTokenTest() throws InterruptedException {

        User user = User.builder().userType(UserType.EMPLOYER).firstName("abc").lastName("bbc")
                .username("djbabs")
                .email("abc@gmail.com").dialingCode("234").phoneNumber("8176267145").isEnabled(Boolean.FALSE)
                .uuid("12323344555").password(passwordEncoder.encode("password")).build();

        user = userService.save(user);

        UserConfirmationToken confirmationToken = new UserConfirmationToken(user,1);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 1);
        confirmationToken.setExpiryDate(cal.getTime());

        userConfirmationTokenRepository.save(confirmationToken);

        TimeUnit.SECONDS.sleep(2);

        int result = userConfirmationTokenService.confirmUserAccountByToken(confirmationToken.getToken());
        Assert.assertEquals(1,result);

    }

    @After
    public  void tearDown(){
        userConfirmationTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void successTokenTest() {

        User user = User.builder().userType(UserType.EMPLOYER).firstName("abc").lastName("bbc")
                .username("djbabs")
                .email("abc@gmail.com").dialingCode("234").phoneNumber("8176267145").isEnabled(Boolean.FALSE)
                .uuid("12323344555").password(passwordEncoder.encode("password")).build();

        user = userService.save(user);

        UserConfirmationToken confirmationToken = new UserConfirmationToken(user,1);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 1);
        confirmationToken.setExpiryDate(cal.getTime());

        userConfirmationTokenRepository.save(confirmationToken);

        int result = userConfirmationTokenService.confirmUserAccountByToken(confirmationToken.getToken());
        Assert.assertEquals(2,result);

        user = userService.findByEmail("abc@gmail.com").get();
        Assert.assertEquals(Boolean.TRUE,user.getIsEnabled());

    }
}
