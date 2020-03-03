package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler.EmailPoller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailPollerTest {

    private EmailPoller sut;

    @BeforeAll
    public void SetUp() {

        MockitoAnnotations.initMocks(this);

//        sut = new EmailPoller();
    }

    @Test
    public void withPollerShouldCallAtLeastOnce() {

//        Mockito.verify(sut, Mockito.atLeastOnce()).pollForEmails();

    }

}
