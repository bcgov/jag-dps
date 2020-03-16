package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Calendar;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DpsEmailParserImplTest {

    private DpsEmailParserImpl sut;

    @BeforeAll
    public void setup() {
        sut = new DpsEmailParserImpl();
    }

    @Test
    public void TestValidEmail1() {

        DpsEmailContent dpsEmailContent = sut.parseEmail(TestKey.VALID_EMAIL_1);

        Assertions.assertEquals("250387-4891", dpsEmailContent.getPhoneNumer());
        Assertions.assertEquals(1, dpsEmailContent.getPageCount());
        Assertions.assertEquals("797276", dpsEmailContent.getJobId());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dpsEmailContent.getDate());

        Assertions.assertEquals(2019, calendar.get(Calendar.YEAR));
        Assertions.assertEquals(11, calendar.get(Calendar.MONTH));

        Assertions.assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
        Assertions.assertEquals(16, calendar.get(Calendar.HOUR_OF_DAY));
        Assertions.assertEquals(13, calendar.get(Calendar.MINUTE));
        Assertions.assertEquals(49, calendar.get(Calendar.SECOND));


    }

    @Test
    public void TestValidEmail2() {

        DpsEmailContent dpsEmailContent = sut.parseEmail(TestKey.VALID_EMAIL_2);

        Assertions.assertEquals("BCGOVTFAX", dpsEmailContent.getPhoneNumer());
        Assertions.assertEquals(2, dpsEmailContent.getPageCount());
        Assertions.assertEquals("642706", dpsEmailContent.getJobId());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dpsEmailContent.getDate());

        Assertions.assertEquals(2019, calendar.get(Calendar.YEAR));
        Assertions.assertEquals(9, calendar.get(Calendar.MONTH));

        Assertions.assertEquals(23, calendar.get(Calendar.DAY_OF_MONTH));
        Assertions.assertEquals(11, calendar.get(Calendar.HOUR_OF_DAY));
        Assertions.assertEquals(50, calendar.get(Calendar.MINUTE));
        Assertions.assertEquals(38, calendar.get(Calendar.SECOND));


    }

}
