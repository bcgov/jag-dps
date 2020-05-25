package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.registration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NoActionRegistrationServiceTest {


    private NoActionRegistrationService sut;

    @BeforeAll
    public void setUp() {
        sut = new NoActionRegistrationService();
    }

    @Test
    public void serviceShouldNotBeActive() {
        Assertions.assertFalse(sut.isActive());
    }


}
