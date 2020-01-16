package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;


@RestController
public class GetValidOpenDFCMCase {
    private static final Logger logger = LogManager.getLogger(GetValidOpenDFCMCase.class);
    /**
     *
     * getValidOpenDFCMCase
     *
     * WARNING: Do not modify input parameters. This operation must replicate legacy
     * system exactly.
     *
     * Note: In the event of an exception, this methods will return an HTTP status
     * code of 200. THIS IS EXPECTED AND WHAT THE LEGACY SYSTEM DOES. PLEASE DO NOT
     * CHANGE THIS. An error is indicated to the calling system by the -2 int
     * value in the response structure.
     *
     * @param driversLicense
     * @param surcode
     *
     *
     */
    @RequestMapping(value = "/getValidOpenDFCMCase",
            produces = {"application/xml"},
            method = RequestMethod.GET)
    @ApiOperation(value = "Generates application callback endpoints for DPS Validation Service", notes = "", tags = {"DPSValidationService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation")})
    public String getValidOpenDFCMCase(@ApiParam(value = "driversLicense", required = true) @RequestParam(value = "driversLicense", required = true) String driversLicense, @ApiParam(value = "surcode", required = true) @RequestParam(value = "surcode", required = true) String surcode) {
        /* TODO implement the logic as in the webmethods with the ORDS pieces in  */
        logger.info("getValidOpenDFCMCase is called");

        String integer_ = "2"; // This will be changed later to accept the int value from the ORDS
        String caseDesc = "ROUTINE - PROFESSIONAL"; // This will be changed later to accept the caseDesc value from the ORDS

        boolean match_driversLicense = Pattern.matches("[0-9]{7}", driversLicense);
        boolean match_surcode = Pattern.matches("^[a-zA-Z&-.]{0,3}", surcode);

        if (!match_driversLicense || !match_surcode)
            return String.format(DpsValidationserviceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE, -2);

        return String.format(DpsValidationserviceConstants.VALIDOPEN_DFCMCASE_RESPONSE, integer_, caseDesc);
    }
    /**
     * handleMissingParams - Missing parameter handler.
     *
     * Note: This method, when invoked in the absence of a required parameter, will
     * return an HTTP status code of 200. THIS IS EXPECTED AND WHAT THE LEGACY
     * SYSTEM DOES. PLEASE DO NOT CHANGE THIS.
     *
     * Returns legacy system equivalent response when required parameters missing
     * from request.
     *
     * @param ex
     * @return a xml
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String paramName = ex.getParameterName();
        logger.fatal("Exception in  : GetValidOpenDFCMCase " + ex.getMessage());

        return String.format(DpsValidationserviceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE, -2);
    }

}
