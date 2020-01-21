package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcsm;

import ca.bc.gov.open.ords.dfcms.client.api.DefaultApi;
import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.DpsValidationServiceConstants;
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
public class ValidationController {
    private static final Logger logger = LogManager.getLogger(ValidationController.class);
    public static final String DL_REGEX = "[0-9]{7}";
    public static final String SURCODE_REGEX = "^[a-zA-Z&-.]{0,3}";

    private final DefaultApi ordsDfcmsApi;

    public ValidationController(DefaultApi ordsDfcmsApi) {
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
     *  @param driversLicense
     * @param surcode
     */
    @RequestMapping(value = "/getValidOpenDFCMCase",
            produces = MediaType.APPLICATION_XML_VALUE,
            method = RequestMethod.GET)
    @ApiOperation(value = "Driver Fitness Case Management Validation Service", notes = "", tags = {
            "DPSValidationService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = GetValidOpenDFCMCase.class) })
    public GetValidOpenDFCMCase getValidOpenDFCMCase(@ApiParam(value = "driversLicense", required = true) @RequestParam(value =
            "driversLicense", required = true) String driversLicense,
                                                          @ApiParam(value = "surcode", required = true) @RequestParam(value = "surcode",
                                               required = true) String surcode) {

        logger.debug("Attempting to get Valid Open Dfcm Case");


        if (!driversLicense.matches(DL_REGEX)) {
            logger.error("Invalid driversLicense format.");
            return GetValidOpenDFCMCase.ErrorResponse();
        }

        if(!surcode.matches(SURCODE_REGEX)) {
            logger.error("Invalid surcode format.");
            return GetValidOpenDFCMCase.ErrorResponse();
        }

        return GetValidOpenDFCMCase.SuccessResponse("ROUTINE - PROFESSIONAL");
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
    public ResponseEntity<GetValidOpenDFCMCase> handleMissingParams(MissingServletRequestParameterException ex) {

        String paramName = ex.getParameterName();
        logger.error("Exception in  : ValidationController " + ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        return new ResponseEntity(new GetValidOpenDFCMCase(2), headers, HttpStatus.OK);
    }


}
