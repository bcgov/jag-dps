package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import ca.bc.gov.open.ottsoa.ords.client.OtssoaService;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsregistrationapi.generated.models.SetRegisterObjectRequest;
import ca.bc.gov.open.pssg.rsbc.dps.dpsregistrationapi.generated.models.SetRegisterObjectResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationServiceEndpointRegisterObjectTest {

    public static final String REG_STATE = "2020";
    private RegistrationServiceEndpoint sut;

    @Mock
    private OtssoaService otssoaService;

    @BeforeAll
    public void setUp() throws ApiException {

        MockitoAnnotations.initMocks(this);

        sut = new RegistrationServiceEndpoint(otssoaService);

    }


    @Test
    public void withValidPayloadShouldRegisterObject() {


        SetRegisterObjectRequest request = new SetRegisterObjectRequest();

        SetRegisterObjectResponse actual = sut.registerObject(request);

        Assertions.assertEquals(REG_STATE, actual.getSetRegisterObjectResponse().getResponseCd());

    }

}
