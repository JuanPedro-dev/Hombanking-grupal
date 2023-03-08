package com.santander.homebanking;

import com.santander.homebanking.services.implement.CreditCardImplService;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value = Suite.class)
@Suite.SuiteClasses({
        AccountImplServicesTest.class,
        CardImplServiceTest.class,
        CardUtilsTests.class,
        ClientImplServiceTests.class,
        CreditCardImplServiceTests.class
})
public class AllTests {
}
