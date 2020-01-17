package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice;

import ca.bc.gov.open.ords.dfcms.client.api.DefaultApi;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;


@RestController
public class GetValidOpenDFCMCase {
    private static final Logger logger = LogManager.getLogger(GetValidOpenDFCMCase.class);

    private final DefaultApi ordsDfcmsApi;

    public GetValidOpenDFCMCase(DefaultApi ordsDfcmsApi) {
        this.ordsDfcmsApi = ordsDfcmsApi;
    }


    /**
     * getValidOpenDFCMCase
     * <p>
     * WARNING: Do not modify input parameters. This operation must replicate legacy
     * system exactly.
     * <p>
     * Note: In the event of a unexpected parameter patter this methods will return an HTTP status
     * code of 200. THIS IS EXPECTED AND WHAT THE LEGACY SYSTEM DOES. PLEASE DO NOT
     * CHANGE THIS. An error is indicated to the calling system by the -2 int
     * value in the response structure.
     *
     * @param driversLicense
     * @param surcode
     */
    @RequestMapping(value = "/getValidOpenDFCMCase",
            produces = {"application/xml"},
            method = RequestMethod.GET)
    @ApiOperation(value = "Driver Fitness Case Management Validation Service", notes = "", tags = {"DPSValidationService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation")})
    public String getValidOpenDFCMCase(@ApiParam(value = "driversLicense", required = true) @RequestParam(value = "driversLicense", required = true) String driversLicense, @ApiParam(value = "surcode", required = true) @RequestParam(value = "surcode", required = true) String surcode) {
        /* TODO implement the logic as in the webmethods with the ORDS pieces in  */
        logger.info("getValidOpenDFCMCase is called");

        String integer_ = "2"; // This will be changed later to accept the int value from the ORDS
        String caseDesc = "ROUTINE - PROFESSIONAL"; // This will be changed later to accept the caseDesc value from the ORDS

        boolean match_driversLicense = Pattern.matches("[0-9]{7}", driversLicense);
        boolean match_surcode = Pattern.matches("^[a-zA-Z&-.]{0,3}", surcode);

        if (!match_driversLicense) {
            logger.fatal("Invalid driversLicense format.");
            return String.format(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE, DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE_CD);
        }
        if (!match_surcode) {
            logger.fatal("Invalid surcode format.");
            return String.format(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE, DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE_CD);
        }

        return String.format(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_RESPONSE, integer_, caseDesc);
    }

    /**
     * handleMissingParams - Missing parameter handler.
     * <p>
     * Note: This method, when invoked in the absence of a required parameter, will
     * return an HTTP status code of 200. THIS IS EXPECTED AND WHAT THE LEGACY
     * SYSTEM DOES. PLEASE DO NOT CHANGE THIS.
     * <p>
     * Returns legacy system equivalent response when required parameters missing
     * from request.
     *
     * @param ex
     * @return a String with media type application/xml
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String paramName = ex.getParameterName();
        logger.fatal("Exception in  : GetValidOpenDFCMCase " + ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        return new ResponseEntity<String>(String.format(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE, DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE_CD), headers, HttpStatus.OK);
    }

}
