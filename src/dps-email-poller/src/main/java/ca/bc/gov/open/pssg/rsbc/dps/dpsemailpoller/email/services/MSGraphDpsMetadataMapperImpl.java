package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.models.DpsEmailContent;
import ca.bc.gov.open.pssg.rsbc.models.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.Message;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;

import java.util.Date;

public class MSGraphDpsMetadataMapperImpl implements MSGraphDpsMetadataMapper {


    public static final String INBOUND = "inbound";
    public static final String FAX = "FAX";
    private final DpsEmailParser dpsEmailParser;

    public MSGraphDpsMetadataMapperImpl(DpsEmailParser dpsEmailParser) {
        this.dpsEmailParser = dpsEmailParser;
    }

    @Override
    public DpsMetadata map(Message emailMessage, DpsFileInfo dpsFileInfo, String tenant) {

        try {

            String body = getEmailBodyText(emailMessage);

            DpsEmailContent dpsEmailContent = dpsEmailParser.parseEmail(body);

            return new DpsMetadata.Builder()
                    .withEmailId(emailMessage.getId())
                    .withApplicationID(tenant)
                    .withDirection(INBOUND)
                    .withInboundChannelType(FAX)
                    .withInboundChannelID(getFirstRecipientEmailLocalPart(emailMessage))
                    .withTo(getFirstRecipientEmailAddress(emailMessage))
                    .withFrom(getFromEmailAddress(emailMessage))
                    .withSubject(emailMessage.getSubject())
                    .withRecvdate(Date.from(emailMessage.getReceivedDateTime().toInstant()))
                    .withSentdate(Date.from(emailMessage.getSentDateTime().toInstant()))
                    .withBody(emailMessage.getBody().toString())
                    .withFaxJobID(dpsEmailContent.getJobId())
                    .withOriginatingNumber(dpsEmailContent.getPhoneNumer())
                    .withNumberOfPages(dpsEmailContent.getPageCount())
                    .withFileInfo(dpsFileInfo)
                    .build();

        } catch (ServiceLocalException e) {
            throw new DpsEmailException("exception");
        }

    }

    private String getFirstRecipientEmailLocalPart(Message emailMessage) throws ServiceLocalException {
        String email = getFirstRecipientEmailAddress(emailMessage);
        return email.substring(0, email.indexOf('@'));
    }

    private String getFirstRecipientEmailAddress(Message emailMessage) throws ServiceLocalException {
        return getFirstRecipients(emailMessage).getAddress();

    }

    private String getFromEmailAddress(Message emailMessage) throws ServiceLocalException {
        if (emailMessage.getFrom() == null) throw new DpsEmailException("From is null.");
        return emailMessage.getFrom().getEmailAddress().toString();
    }


    private EmailAddress getFirstRecipients(Message emailMessage) throws ServiceLocalException {
        if (emailMessage.getToRecipients() == null) throw new DpsEmailException("Recipient is null.");
        if (emailMessage.getToRecipients().isEmpty()) throw new DpsEmailException("Recipient is null.");
        return emailMessage.getToRecipients().get(0).getEmailAddress();
    }


    private String getEmailBodyText(Message emailMessage) throws ServiceLocalException {
        return emailMessage.getBody().toString();
    }

}