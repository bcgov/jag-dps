package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import ca.bc.gov.open.ottsoa.ords.client.models.CreateObjectRequest;
import ca.bc.gov.open.ottsoa.ords.client.models.CreatePackageRequest;
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

        SetRegisterObjectResponse2 registerObjectResponse = new SetRegisterObjectResponse2();

        CreateObjectRequest createObjectRequest = new CreateObjectRequest
                .Builder()
                .withImportGuid(UUID.fromString(request.getPackage().getValue()))
                .withActionMethod(request.getActionMethod().getValue())
                .withActionSystem(request.getActionSystem().getValue())
                .withActionUser(request.getActionUser().getValue())
                .withCaseResults(request.getCaseResult().getValue())
                .withCaseUpdate(request.getCaseUpdate().getValue())
                .withClientName(request.getClientName().getValue())
                .withClientNumber(request.getClientNum().getValue())
                .withCompletionDate(DateUtils.toDate(request.getCompletionDTM().getValue()))
                .withContentId(request.getContentID().getValue())
                .withImageUpload(request.getImageUpload().getValue())
                .withPackageFormatType(request.getPackageFormatType())
                .build();

        try {

            logger.debug("Attempting to create package using ords api.");
            DefaultResponse apiResponse = otssoaService.createObject(createObjectRequest);

            if(apiResponse.getRegState() == "0") {
                logger.info("Successfully created package in otssoa database.");
            } else {
                logger.error("Error while creating package in otssoa database.");
            }

            registerObjectResponse.setResponseCd(apiResponse.getRegState());
            registerObjectResponse.setResponseMsg(apiResponse.getErrorMessage());

        } catch (ApiException ex) {

            logger.error("exception while trying to created package in otssoa database.", ex);
            registerObjectResponse.setResponseCd(Integer.toString(Keys.ERROR_STATUS_CODE));
            registerObjectResponse.setResponseMsg(MessageFormat.format("Status code: {0}, error {1}", ex.getCode(), ex.getMessage()));

        }

        response.setSetRegisterObjectResponse(registerObjectResponse);

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
                .withPageCount(tryParseInt(request.getPageCount().getValue()))
                .withProgramType(request.getProgramType().getValue())
                .withReceivedDate(DateUtils.toDate(request.getReceivedDTM()))
                .withRecordCount(tryParseInt(request.getRecordCount().getValue()))
                .build();

        try {

            logger.debug("Attempting to create package using ords api.");
            DefaultResponse apiResponse = otssoaService.createPackage(createPackageRequest);

            if(apiResponse.getRegState() == "0") {
                logger.info("Successfully created package in otssoa database.");
            } else {
                logger.error("Error while creating package in otssoa database.");
            }

            registerPackageResponse.setResponseCd(apiResponse.getRegState());
            registerPackageResponse.setResponseMsg(apiResponse.getErrorMessage());


        } catch (ApiException ex) {
            logger.error("exception while trying to created package in otssoa database.", ex);
            registerPackageResponse.setResponseCd(Integer.toString(Keys.ERROR_STATUS_CODE));
            registerPackageResponse.setResponseMsg(MessageFormat.format("Status code: {0}, error {1}", ex.getCode(), ex.getMessage()));

        }

        response.setSetRegisterPackageResponse(registerPackageResponse);

        return response;

    }

    /**
     * Default to -1 if value is not an integer
     * @param value
     * @return
     */
    private int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch(NumberFormatException nfe) {
            return -1;
        }
    }

}

