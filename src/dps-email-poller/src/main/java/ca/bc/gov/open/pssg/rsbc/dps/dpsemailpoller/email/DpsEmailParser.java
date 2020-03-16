package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

public interface DpsEmailParser {
    DpsEmailContent parseEmail(String html);
}
