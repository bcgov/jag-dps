package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import ca.bc.gov.open.pssg.rsbc.dps.dpsregistrationapi.generated.models.SetRegisterPackageRequest;
import ca.bc.gov.open.pssg.rsbc.dps.dpsregistrationapi.generated.models.SetRegisterPackageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class registrationServiceEndpointRegisterPackageTest {

    private RegistrationServiceEndpoint sut;


    @BeforeAll
    public void setUp() {
        sut = new RegistrationServiceEndpoint();
    }


    @Test
    public void withValidPayloadShouldReturnExepcted() {


        SetRegisterPackageRequest request = new SetRegisterPackageRequest();

        SetRegisterPackageResponse actual = sut.registerPackage(request);

        Assertions.assertEquals("2020", actual.getSetRegisterPackageResponse().getResponseCd());

    }




}
