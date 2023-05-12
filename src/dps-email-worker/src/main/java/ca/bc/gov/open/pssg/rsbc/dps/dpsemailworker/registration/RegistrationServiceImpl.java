package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.registration;

import ca.bc.gov.open.ottsoa.ords.client.OtssoaService;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;
import ca.bc.gov.open.ottsoa.ords.client.models.CreatePackageRequest;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.Keys;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationServiceImpl implements RegistrationService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OtssoaService otssoaService;

    public RegistrationServiceImpl(OtssoaService otssoaService) {
        this.otssoaService = otssoaService;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void registerPackage(DpsMetadata dpsMetadata) {

        //TODO: Investigate, the tenant is not passed as a parameter where there is a placeholder for it.
        logger.info("creating package request...");
        CreatePackageRequest request = new CreatePackageRequest
                .Builder()
                .withBusinessArea(Keys.REGISTRATION_BUSINESS_AREA)
                .withFormatType(Keys.REGISTRATION_DEFAULT_FORMAT)
                .withRecordCount(Keys.REGISTRATION_DEFAULT_RECORD_COUNT)
                .withSource(dpsMetadata.getOriginatingNumber())
                .withRecipient(dpsMetadata.getTo())
                .withReceivedDate(dpsMetadata.getReceivedDate())
                .withImportGuid(dpsMetadata.getTransactionId().toString())
                .withFilename(dpsMetadata.getFileInfo().getName())
                .withPageCount(dpsMetadata.getNumberOfPages())
                .build();
        logger.info("creating package request completed");
        try {
            DefaultResponse response = otssoaService.createPackage(request);
            logger.info("Done sending to otssoa Service...");
            if(StringUtils.isEmpty(response.getRegState()) || !response.getRegState().equals(Keys.REGISTRATION_OPERATION_SUCCESS_STATUS))
            {
                logger.error("Failed to create package in OTS database: {}", response.getErrorMessage());
                return;
            }

            logger.info("Successfully created package in OTS database");

        } catch (ApiException e) {
            logger.error("Exception while create package in OTS database", e);
        }
    }
}
