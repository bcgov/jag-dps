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
        /* TO DO implement the logic as in the webmethods */
        return "<GetValidOpenDFCMCase>\n" +
                "<int>2</int>\n" +
                "<caseDesc>ROUTINE - PROFESSIONAL</caseDesc>\n" +
                "</GetValidOpenDFCMCase>\n";
    }

}
