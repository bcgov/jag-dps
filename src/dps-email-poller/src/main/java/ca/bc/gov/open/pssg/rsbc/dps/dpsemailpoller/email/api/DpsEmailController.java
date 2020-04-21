package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.api;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.models.DpsEmailResponse;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.EmailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class DpsEmailController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EmailService emailService;

    public DpsEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PutMapping(value = "/email/{id}/processed", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DpsEmailResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = DpsEmailResponse.class)
    })
    @ApiOperation(value = "Mark email as processed", tags = {"DpsEmailProcessing"})
    public ResponseEntity<DpsEmailResponse> Processed(@PathVariable String id, @RequestBody DpsEmailProcessedRequest dpsEmailProcessedRequest) {
        try {
            emailService.moveToProcessedFolder(new String(Base64.getDecoder().decode(id)));
            logger.info("message successfully moved to processed folder, id: [{}]", dpsEmailProcessedRequest.getCorrelationId());
            return new ResponseEntity<>(DpsEmailResponse.Success(), HttpStatus.OK);
        } catch (DpsEmailException ex) {
            return new ResponseEntity<>(DpsEmailResponse.Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/email/{id}/error", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DpsEmailResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = DpsEmailResponse.class)
    })
    @ApiOperation(value = "Mark email as having an error in processing", tags = {"DpsEmailProcessing"})
    public ResponseEntity<DpsEmailResponse> ProcessFailed(@PathVariable String id, @RequestBody DpsEmailProcessedRequest dpsEmailProcessedRequest) {
        try {
            emailService.moveToErrorFolder(new String(Base64.getDecoder().decode(id)));
            logger.info("message successfully moved to error folder, id: [{}]", dpsEmailProcessedRequest.getCorrelationId());
            return new ResponseEntity<>(DpsEmailResponse.Success(), HttpStatus.OK);
        } catch (DpsEmailException ex) {
            return new ResponseEntity<>(DpsEmailResponse.Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
