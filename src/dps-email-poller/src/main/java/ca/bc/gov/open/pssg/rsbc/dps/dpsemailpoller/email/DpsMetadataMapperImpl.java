package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import ca.bc.gov.open.pssg.rsbc.DpsMetadata;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;

public class DpsMetadataMapperImpl implements DpsMetadataMapper {


    public static final String INBOUND = "inbound";
    public static final String FAX = "FAX";
    private final DpsEmailParser dpsEmailParser;

    public DpsMetadataMapperImpl(DpsEmailParser dpsEmailParser) {
        this.dpsEmailParser = dpsEmailParser;
    }

    @Override
    public DpsMetadata map(EmailMessage emailMessage, String tenant) {

        try {

            String body = getEmailBodyText(emailMessage);

            DpsEmailContent dpsEmailContent = dpsEmailParser.parseEmail(body);

            return new DpsMetadata.Builder()
                    .withApplicationID(tenant)
                    .withDirection(INBOUND)
                    .withInboundChannelType(FAX)
                    .withInboundChannelID(getFirstRecipientEmailLocalPart(emailMessage))
                    .withTo(getFirstRecipientEmailAddress(emailMessage))
                    .withFrom(getFromEmailAddress(emailMessage))
                    .withSubject(emailMessage.getSubject())
                    .withRecvdate(emailMessage.getDateTimeReceived())
                    .withSentdate(emailMessage.getDateTimeSent())
                    .withBody(emailMessage.getBody().toString())
                    .withFaxJobID(dpsEmailContent.getJobId())
                    .withOriginatingNumber(dpsEmailContent.getPhoneNumer())
                    .withNumberOfPages(dpsEmailContent.getPageCount())
                    .build();

        } catch (ServiceLocalException e) {
            throw new DpsEmailException("exception");
        }


    }

    private String getFirstRecipientEmailLocalPart(EmailMessage emailMessage) throws ServiceLocalException {
        String email = getFirstRecipientEmailAddress(emailMessage);
        return email.substring(0, email.indexOf('@'));
    }

    private String getFirstRecipientEmailAddress(EmailMessage emailMessage) throws ServiceLocalException {
        return getFirstRecipients(emailMessage).getAddress();

    }

    private String getFromEmailAddress(EmailMessage emailMessage) throws ServiceLocalException {
        if (emailMessage.getFrom() == null) throw new DpsEmailException("From is null.");
        return emailMessage.getFrom().getAddress();
    }


    private EmailAddress getFirstRecipients(EmailMessage emailMessage) throws ServiceLocalException {
        if (emailMessage.getToRecipients() == null) throw new DpsEmailException("Recipient is null.");
        if (emailMessage.getToRecipients().getItems().isEmpty()) throw new DpsEmailException("Recipient is null.");
        return emailMessage.getToRecipients().getItems().get(0);
    }


    private String getEmailBodyText(EmailMessage emailMessage) throws ServiceLocalException {
        return emailMessage.getBody().toString();
    }




}