package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import ca.bc.gov.open.pssg.rsbc.dps.dpsregistrationapi.generated.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class RegistrationServiceEndpoint {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @PayloadRoot(namespace = Keys.NAMESPACE_URI, localPart = Keys.REGISTRATION_SERVICE_OBJECT_REQUEST)
    @ResponsePayload
    public SetRegisterObjectResponse registerObject(@RequestPayload SetRegisterObjectRequest request) {


        SetRegisterObjectResponse response= new SetRegisterObjectResponse();

        SetRegisterObjectResponse2 fakeResponse = new SetRegisterObjectResponse2();
        fakeResponse.setResponseCd("2020");
        fakeResponse.setResponseMsg("Yo we are in 2020 and I'm still coding soap services!");
        response.setSetRegisterObjectResponse(fakeResponse);

        return response;
    }

    @PayloadRoot(namespace = Keys.NAMESPACE_URI, localPart = Keys.REGISTRATION_SERVICE_PACKAGE_REQUEST)
    @ResponsePayload
    public SetRegisterPackageResponse registerPackage(@RequestPayload SetRegisterPackageRequest request) {


        SetRegisterPackageResponse response= new SetRegisterPackageResponse();

        SetRegisterPackageResponse2 fakeResponse = new SetRegisterPackageResponse2();
        fakeResponse.setResponseCd("2020");
        fakeResponse.setResponseMsg("Yo we are in 2020 and I'm still coding soap services!");
        response.setSetRegisterPackageResponse(fakeResponse);

        return response;
    }

}

