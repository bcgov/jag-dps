package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import ca.bc.gov.open.ottsoa.ords.client.CreatePackageRequest;
import ca.bc.gov.open.ottsoa.ords.client.OtssoaService;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;
import ca.bc.gov.open.pssg.rsbc.dps.dpsregistrationapi.generated.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.text.MessageFormat;
import java.util.UUID;

@Endpoint
public class RegistrationServiceEndpoint {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OtssoaService otssoaService;

    public RegistrationServiceEndpoint(OtssoaService otssoaService) {
        this.otssoaService = otssoaService;
    }

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
        SetRegisterPackageResponse2 registerPackageResponse = new SetRegisterPackageResponse2();

        CreatePackageRequest createPackageRequest = new CreatePackageRequest
                .Builder()
                .withSource(request.getSource().getValue())
                .withBusinessArea(request.getBusinessAreaCD())
                .withFilename(request.getFilename())
                .withImportGuid(UUID.fromString(request.getImportGUID()))
                .withFormatType(request.getPackageFormatType())
                .withPageCount(Integer.valueOf(request.getPageCount().getValue()))
                .withProgramType(request.getProgramType().getValue())
                .withReceivedDate(DateUtils.toDate(request.getReceivedDTM()))
                .withRecordCount(Integer.parseInt(request.getRecordCount().getValue()))
                .build();

        try {

            logger.debug("Attempting to create package using ords api.");
            DefaultResponse apiResponse = otssoaService.CreatePackage(createPackageRequest);
            registerPackageResponse.setResponseCd(apiResponse.getRegState());
            registerPackageResponse.setResponseMsg(apiResponse.getErrorMessage());
            logger.info("Successfully created package in otssoa database.");

        } catch (ApiException ex) {
            logger.error("exception while trying to created package in otssoa database.", ex);
            registerPackageResponse.setResponseCd(Integer.toString(Keys.ERROR_STATUS_CODE));
            registerPackageResponse.setResponseMsg(MessageFormat.format("Status code: {0}, error {1}", ex.getCode(), ex.getMessage()));

        }

        response.setSetRegisterPackageResponse(registerPackageResponse);

        return response;

    }

}

