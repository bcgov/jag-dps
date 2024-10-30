package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.component;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.configuration.MSGraphServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import jakarta.annotation.PostConstruct;

/**
 * 
 * Provides a GraphServiceClient
 * 
 * Reference: https://learn.microsoft.com/en-us/graph/sdks/choose-authentication-providers?tabs=java#client-credentials-provider
 *  
 * If the expiry date of the Secret key is coming up due, send a notification. 
 * 
 */
@Component
public class GraphServiceClientComp {

	private static final Logger logger = LoggerFactory.getLogger(GraphServiceClientComp.class);

	private String clientId;
	private String tenantId;
	private String clientSecret;

	private GraphServiceClient graphClient = null;

	private MSGraphServiceProperties props;

	public GraphServiceClientComp(MSGraphServiceProperties props) {
		this.props = props;
	}

	@PostConstruct
	private void postConstruct() {

		try {

			this.clientId = props.getMsgClientId();
			this.tenantId = props.getMsgAuthority();
			this.clientSecret = props.getMsgSecretKey();

			// The client credentials flow requires that you request the
			// /.default scope, and pre-configure your permissions on the
			// app registration in Azure. An administrator must grant consent
			// to those permissions beforehand.
			final String[] scopes = new String[] { props.getMsgEndpointHost() };

			final ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
					.tenantId(tenantId).clientSecret(clientSecret).build();

			if (null == scopes || null == credential) {
				throw new Exception("Unexpected error");
			}

			this.graphClient = new GraphServiceClient(credential, scopes);

			logger.info("MS Graph Service Client created.");

		} catch (Exception ex) {
			
			logger.error("Unable to create MS Graph Service client. Check CLientId, Authority and/or Secret. Err: " + ex.getMessage());
		}

	}

	public GraphServiceClient getGraphClient() {
		return graphClient;
	}

}
