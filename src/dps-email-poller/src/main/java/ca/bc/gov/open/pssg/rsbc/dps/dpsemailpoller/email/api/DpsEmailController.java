package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.api;

import ca.bc.gov.open.pssg.rsbc.DpsMetadata;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.models.DpsEmailResponse;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.EmailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DpsEmailController {

    private final EmailService emailService;

    public DpsEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/email/processed", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Mark email as processed", tags = {"DpsEmailProcessing"})
    public ResponseEntity<DpsEmailResponse> Processed(@RequestBody DpsMetadata dpsMetadata) {
        try {
            emailService.moveToProcessedFolder(dpsMetadata.getEmailId());
            return new ResponseEntity<>(DpsEmailResponse.Success(), HttpStatus.OK);
        } catch (DpsEmailException ex) {
            return new ResponseEntity<>(DpsEmailResponse.Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
