package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetValidOpenDFCMCase {
    private static final Logger logger = LogManager.getLogger(GetValidOpenDFCMCase.class);

    @RequestMapping(value = "/getValidOpenDFCMCase",
            produces = {"application/xml"},
            method = RequestMethod.GET)
    @ApiOperation(value = "Generates application callback endpoints for DPS Validation Service", notes = "", tags = {"DPSValidationService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation")})
    public String getValidOpenDFCMCase(@ApiParam(value = "driversLicense", required = true) @RequestParam(value = "driversLicense", required = true) String driversLicense, @ApiParam(value = "surcode", required = true) @RequestParam(value = "surcode", required = true) String surcode) {
        /* TODO implement the logic as in the webmethods */
        logger.info("getValidOpenDFCMCase is called");
        String integer_ = "2"; // This will be changed later to accept the int value from the ORDS
        String caseDesc = "ROUTINE - PROFESSIONAL"; // This will be changed later to accept the caseDesc value from the ORDS

        return String.format("<GetValidOpenDFCMCase>\n" +
                "<int>%s</int>\n" +
                "<caseDesc>%s</caseDesc>\n" +
                "</GetValidOpenDFCMCase>\n", integer_, caseDesc);
    }

}
