package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsMSGraphException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.component.GraphServiceClientComp;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.configuration.MSGraphServiceProperties;
import com.microsoft.graph.models.*;
import com.microsoft.graph.models.MailFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

/**
 * 
 * MS Graph Email Service methods
 * 
 * @author 176899
 * 
 * Permissions required (to-date)
 *  
 *  Mail.Read
 *  Mail.Send 
 * 	Mail.ReadWrite
 *
 */
@Service
public class MSGraphServiceImpl implements MSGraphService {

	private static final Logger logger = LoggerFactory.getLogger(MSGraphServiceImpl.class);
	public final static String dateFormat = "yyyy-MM-dd";

	private MSGraphServiceProperties props;
	private GraphServiceClientComp gComp;

	public MSGraphServiceImpl(MSGraphServiceProperties props, GraphServiceClientComp gComp) {
		this.props = props;
		this.gComp = gComp;
	}

	@PostConstruct
	private void postConstruct() {
		logger.info("MS Graph Service started.");
	}

	/**
	 * Read inbox messages
	 *  Reference: // https://learn.microsoft.com/en-us/graph/api/message-get?view=graph-rest-1.0&tabs=http
	 *  Permissions (from least to most privileged):  Mail.ReadBasic.All, Mail.Read
	 */
	@Override
	public List<Message> GetMessages(String filter) throws DpsMSGraphException {
		try {
			MessageCollectionResponse messageCollectionResponse = gComp.getGraphClient().users().byUserId(props.getMsgEmailAccount()).mailFolders().byMailFolderId("Inbox")
					.messages().get(requestConfiguration -> {
						if (!filter.isEmpty()) requestConfiguration.queryParameters.filter = filter;
						if (props.getMsgEmailPerBatch() > 0) requestConfiguration.queryParameters.top = props.getMsgEmailPerBatch();
					});

			return messageCollectionResponse.getValue();
		} catch (Exception e) {
			throw new DpsMSGraphException("Exception while getting emails from inbox", e.getCause());
		}
	}

	/**
	 * Get attachments of a message
	 *  Reference: // https://learn.microsoft.com/en-us/graph/api/attachment-get?view=graph-rest-1.0&tabs=http
	 *  Permissions (from least to most privileged):  Mail.Read
	 */
	@Override
	public List<Attachment> getAttachments(String id) throws DpsMSGraphException {
		try {
			AttachmentCollectionResponse response = gComp.getGraphClient().users().byUserId(props.getMsgEmailAccount()).messages().byMessageId(id).attachments().get();
			return response.getValue();
		} catch (Exception e) {
			throw new DpsMSGraphException("Exception while reading email attachments", e.getCause());
		}
	}

	/**
	 * send message
	 *  Reference: // https://learn.microsoft.com/en-us/graph/api/message-send?view=graph-rest-1.0&tabs=http
	 *  Permissions (from least to most privileged):  Mail.Send
	 */
	@Override
	public void sendMessage(Message message, boolean saveToSentItems) throws DpsMSGraphException {
		try {
			com.microsoft.graph.users.item.sendmail.SendMailPostRequestBody sendMailPostRequestBody = new com.microsoft.graph.users.item.sendmail.SendMailPostRequestBody();
			sendMailPostRequestBody.setSaveToSentItems(saveToSentItems);
			sendMailPostRequestBody.setMessage(message);
			gComp.getGraphClient().users().byUserId(props.getMsgEmailAccount()).sendMail().post(sendMailPostRequestBody);
		} catch (Exception e) {
			throw new DpsMSGraphException("Exception while sending email", e.getCause());
		}
	}

	@Override
	public byte[] getAttachmentContent(Attachment attachment) throws DpsMSGraphException {
		if (attachment instanceof FileAttachment) {
			try {
				FileAttachment fileAttachment = (FileAttachment) attachment;
				return fileAttachment.getContentBytes();
			} catch (Exception e) {
				throw new DpsMSGraphException("Exception while reading email attachment content", e.getCause());
			}
		}
		else {
			throw new DpsMSGraphException("Attachment is not a file attachment");
		}
	}

	@Override
	public void deleteMessage(Message message) throws DpsMSGraphException {
		try {
			moveToFolder(message.getId(), "deleteditems");
		} catch (Exception e) {
			throw new DpsMSGraphException("Exception while deleting email", e.getCause());
		}
	}

	/**
	 *  Get credential expiry date
	 *  Reference: // https://learn.microsoft.com/en-us/graph/api/application-get?view=graph-rest-1.0&tabs=http
	 *  Permissions (from least to most privileged):  Application.Read.All
	 */
	@Override
	public String getPasswordCredentialsExpiryDate() throws DpsMSGraphException {
		try {
			Application a = gComp.getGraphClient().applicationsWithAppId(props.getMsgClientId()).get(requestConfiguration -> {
				requestConfiguration.queryParameters.select = new String []{"passwordCredentials"};
			});

			OffsetDateTime dt = a.getPasswordCredentials().get(0).getEndDateTime();
			logger.debug("MS Graph API Secret Key expiration date: " + dt.toLocalDate());
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateFormat);
			return fmt.format(dt);
		} catch (Exception e) {
			throw new DpsMSGraphException("Exception while reading password expiry date", e.getCause());
		}

	}

	public Message moveToFolder(String id, String folderName) throws DpsMSGraphException {
		Optional<String> folderId = getFolderIdByName(folderName);

		if(!folderId.isPresent())  folderId = createFolder(folderName);

		if(!folderId.isPresent())  throw new DpsMSGraphException("Folder not found: " + folderName);

		try {
			return moveItemById(id, folderId.get());
		} catch (Exception e) {
			throw new DpsMSGraphException("Exception while moving folder: " + folderName, e.getCause());
		}
	}

	/**
	 *  Move a message to another folder
	 *  Reference: // https://learn.microsoft.com/en-us/graph/api/message-move?view=graph-rest-1.0&tabs=http
	 *  Permissions (from least to most privileged):  Mail.ReadWrite
	 */
	private Message moveItemById(String inboxItemById, String destinationId) throws Exception{
		com.microsoft.graph.users.item.messages.item.move.MovePostRequestBody movePostRequestBody = new com.microsoft.graph.users.item.messages.item.move.MovePostRequestBody();
		movePostRequestBody.setDestinationId(destinationId);

		// This requires permissions: Mail.ReadWrite
		return gComp.getGraphClient().users().byUserId(props.getMsgEmailAccount()).messages().byMessageId(inboxItemById).move()
				.post(movePostRequestBody);
	}

	/**
	 *  List mailFolders
	 *  Reference: // https://learn.microsoft.com/en-us/graph/api/user-list-mailfolders?view=graph-rest-1.0&tabs=http
	 *  Permissions (from least to most privileged):  Mail.ReadBasic.All
	 */
	private Optional<String> getFolderIdByName(String folderName) {
		try {
			// Get the list of mail folders
			MailFolderCollectionResponse resp = gComp.getGraphClient().users().byUserId(props.getMsgEmailAccount()).mailFolders().byMailFolderId("Inbox").childFolders().get();

			// Iterate through the list of folders to find the one with the desired name
			for (MailFolder folder : resp.getValue()) {
				if (folder.getDisplayName().equalsIgnoreCase(folderName)) {
					return Optional.of(folder.getId());
				}
			}
			return Optional.empty();
		} catch (Exception e){
			return Optional.empty();
		}
	}

	/**
	 *  create a new mail folder
	 *  Reference: // https://learn.microsoft.com/en-us/graph/api/user-post-mailfolders?view=graph-rest-1.0&tabs=http
	 *  Permissions (from least to most privileged):  Mail.ReadWrite
	 */
	public Optional<String> createFolder(String folderName) {
		try {
			// Create a new MailFolder object
			MailFolder newFolder = new MailFolder();
			newFolder.setDisplayName(folderName);

			// Use the Graph API to create the new folder in the user's inbox
			MailFolder createdFolder = gComp.getGraphClient().users().byUserId(props.getMsgEmailAccount())
					.mailFolders().byMailFolderId("Inbox").childFolders().post(newFolder);

			// Return the ID of the newly created folder
			return Optional.of(createdFolder.getId());
		} catch (Exception e){
			return Optional.empty();
		}
	}
}
