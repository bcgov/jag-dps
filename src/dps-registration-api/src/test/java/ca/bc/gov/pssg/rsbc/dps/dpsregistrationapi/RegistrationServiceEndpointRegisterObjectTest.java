package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import ca.bc.gov.open.pssg.rsbc.dps.dpsregistrationapi.generated.models.SetRegisterObjectRequest;
import ca.bc.gov.open.pssg.rsbc.dps.dpsregistrationapi.generated.models.SetRegisterObjectResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationServiceEndpointRegisterObjectTest {

    private RegistrationServiceEndpoint sut;

    @BeforeAll
    public void setUp() {
        sut = new RegistrationServiceEndpoint();
    }


    @Test
    public void withValidPayloadShouldRegisterObject() {


        SetRegisterObjectRequest request = new SetRegisterObjectRequest();

        SetRegisterObjectResponse actual = sut.registerObject(request);

        Assertions.assertEquals("2020", actual.getSetRegisterObjectResponse().getResponseCd());

    }

}
