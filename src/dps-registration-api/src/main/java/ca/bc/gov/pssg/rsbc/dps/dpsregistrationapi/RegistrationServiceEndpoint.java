package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import bcgov.reeks.dps_registrationservices_wsprovider.dpsdocumentstatusregws.*;
import ca.bc.gov.dps.monitoring.NotificationService;
import ca.bc.gov.dps.monitoring.SystemNotification;
import ca.bc.gov.open.ottsoa.ords.client.OtssoaService;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;
import ca.bc.gov.open.ottsoa.ords.client.models.CreateObjectRequest;
import ca.bc.gov.open.ottsoa.ords.client.models.CreatePackageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class RegistrationServiceEndpoint implements DpsDocumentStatusRegWSPortType {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OtssoaService otssoaService;


    public RegistrationServiceEndpoint(OtssoaService otssoaService) {
        this.otssoaService = otssoaService;
    }

    @Override
    public SetRegisterObjectResponse2 setRegisterObject(SetRegisterObjectRequest setRegisterObjectRequest) {

        ObjectFactory factory = new ObjectFactory();

        SetRegisterObjectResponse response = factory.createSetRegisterObjectResponse();

        SetRegisterObjectResponse2 registerObjectResponse = factory.createSetRegisterObjectResponse2();

        CreateObjectRequest createObjectRequest = new CreateObjectRequest
                .Builder()
                .withImportGuid(setRegisterObjectRequest.getPackage().getValue())
                .withActionMethod(setRegisterObjectRequest.getActionMethod().getValue())
                .withActionSystem(setRegisterObjectRequest.getActionSystem().getValue())
                .withActionUser(setRegisterObjectRequest.getActionUser().getValue())
                .withCaseResults(setRegisterObjectRequest.getCaseResult().getValue())
                .withCaseUpdate(setRegisterObjectRequest.getCaseUpdate().getValue())
                .withClientName(setRegisterObjectRequest.getClientName().getValue())
                .withClientNumber(setRegisterObjectRequest.getClientNum().getValue())
                .withCompletionDate(DateUtils.toDate(setRegisterObjectRequest.getCompletionDTM().getValue()))
                .withContentId(setRegisterObjectRequest.getContentID().getValue())
                .withContentType(setRegisterObjectRequest.getType())
                .withImageUpload(setRegisterObjectRequest.getImageUpload().getValue())
                .withPackageFormatType(setRegisterObjectRequest.getPackageFormatType())
                .build();

        try {

            logger.debug("Attempting to create package using ords api.");
            DefaultResponse apiResponse = otssoaService.createObject(createObjectRequest);

            if (apiResponse.getRegState().equals("0")) {
                logger.info("Successfully created package in otssoa database.");
            } else {
                logger.error("Error while creating package in otssoa database.");
                notifyError(
                        apiResponse.getErrorMessage(),
                        "REGISTER OBJECT ERROR",
                        setRegisterObjectRequest.getPackage() != null ? setRegisterObjectRequest.getPackage().getValue(): "",
                        setRegisterObjectRequest.getImageUpload() != null ? setRegisterObjectRequest.getImageUpload().getValue():"",
                        "Registration Api - Object");
            }

            registerObjectResponse.setResponseCd(apiResponse.getRegState());
            registerObjectResponse.setResponseMsg(apiResponse.getErrorMessage());

        } catch (ApiException ex) {

            logger.error("exception while trying to created package in otssoa database.", ex);
            registerObjectResponse.setResponseCd(Integer.toString(Keys.ERROR_STATUS_CODE));
            registerObjectResponse.setResponseMsg(
                    MessageFormat.format(
                            "Status code: {0}, error {1}",
                            ex.getCode(),
                            ex.getMessage()));

        }

        response.setSetRegisterObjectResponse(registerObjectResponse);

        return registerObjectResponse;
    }

    @Override
    public SetRegisterPackageResponse2 setRegisterPackage(SetRegisterPackageRequest setRegisterPackageRequest) {

        SetRegisterPackageResponse response = new SetRegisterPackageResponse();
        SetRegisterPackageResponse2 registerPackageResponse = new SetRegisterPackageResponse2();

        CreatePackageRequest createPackageRequest = new CreatePackageRequest
                .Builder()
                .withSource(setRegisterPackageRequest.getSource().getValue())
                .withBusinessArea(setRegisterPackageRequest.getBusinessAreaCD())
                .withFilename(setRegisterPackageRequest.getFilename())
                .withImportGuid(setRegisterPackageRequest.getImportGUID())
                .withFormatType(setRegisterPackageRequest.getPackageFormatType())
                .withPageCount(tryParseInt(setRegisterPackageRequest.getPageCount().getValue()))
                .withProgramType(setRegisterPackageRequest.getProgramType().getValue())
                .withReceivedDate(DateUtils.toDate(setRegisterPackageRequest.getReceivedDTM()))
                .withRecordCount(tryParseInt(setRegisterPackageRequest.getRecordCount().getValue()))
                .build();

        try {

            logger.debug("Attempting to create package using ords api.");
            DefaultResponse apiResponse = otssoaService.createPackage(createPackageRequest);

            if (apiResponse.getRegState().equals("0")) {
                logger.info("Successfully created package in otssoa database.");
            } else {
                logger.error("Error while creating package in otssoa database.");
                notifyError(
                        apiResponse.getErrorMessage(),
                        "REGISTER OBJECT ERROR",
                        setRegisterPackageRequest.getImportGUID() != null ? setRegisterPackageRequest.getImportGUID() : "",
                        setRegisterPackageRequest.getFilename() != null ? setRegisterPackageRequest.getFilename() : "",
                        "Registration Api - Object");
            }

            registerPackageResponse.setResponseCd(apiResponse.getRegState());
            registerPackageResponse.setResponseMsg(apiResponse.getErrorMessage());


        } catch (ApiException ex) {
            logger.error("exception while trying to created package in otssoa database.", ex);
            registerPackageResponse.setResponseCd(Integer.toString(Keys.ERROR_STATUS_CODE));
            registerPackageResponse.setResponseMsg(MessageFormat.format("Status code: {0}, error {1}", ex.getCode()
                    , ex.getMessage()));

        }

        response.setSetRegisterPackageResponse(registerPackageResponse);

        return registerPackageResponse;
    }

    /**
     * Default to -1 if value is not an integer
     *
     * @param value
     * @return
     */
    private int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }


    private void notifyError(String errorMessage, String type, String id, String fileName, String component) {


        SystemNotification systemNotification = new SystemNotification
                .Builder()
                .withCorrelationId(id)
                .withTransactionId(fileName)
                .withAction("Action to be determined")
                .withApplicationName(Keys.APP_NAME)
                .withComponent(component)
                .withMessage(errorMessage)
                .withType(type)
                .buildError();

        NotificationService.notify(systemNotification);

    }

}

