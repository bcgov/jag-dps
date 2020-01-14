package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetValidOpenDFCMCase {

    @RequestMapping(value = "/getValidOpenDFCMCase",
            produces = {"application/xml"},
            method = RequestMethod.GET)
    public String getValidOpenDFCMCase(@RequestParam(value = "driversLicense", required = true) String driversLicense, @RequestParam(value = "surcode", required = true) String surcode) {
        /* TODO implement the logic as in the webmethods */
        String integer_ = "2"; // This will be changed later to accept the int value from the ORDS
        String caseDesc = "ROUTINE - PROFESSIONAL"; // This will be changed later to accept the caseDesc value from the ORDS
        return String.format("<GetValidOpenDFCMCase>\n" +
                "<int>%s</int>\n" +
                "<caseDesc>%s</caseDesc>\n" +
                "</GetValidOpenDFCMCase>\n", integer_, caseDesc);
    }

}
