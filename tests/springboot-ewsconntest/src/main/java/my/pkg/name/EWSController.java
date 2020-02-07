package my.pkg.name;

import java.net.URI;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.LogicalOperator;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;

@RestController
public class EWSController {

/**
 *
 * Outlook client. 
 * 
 * Demonstrates a filtered fetch of items from your inbox 
 * 	
 * @return
 * @throws Exception
 */
@RequestMapping("/getMessageCount")
public String getMessageCount() throws Exception {	  
	 
	  String email = "your gov email here"; 
	  String passwd ="your idir password here";
	 
	  ExchangeService service = EWSAutodiscoverAPI.connectViaExchangeAutodiscover(email, passwd);
	  
	  // current (DEV) API Gateway endpoint for EWS
	  service.setUrl(new URI("https://wsgw.dev.jag.gov.bc.ca/dps/bcgov/ews/services/Exchange.asmx")); 
	  
	  FindItemsResults<Item> findResults = findItems(service);
	  
	  return Integer.toString(findResults.getTotalCount()) + " Messages found in you Inbox";
	  
  }

  private FindItemsResults<Item> findItems(ExchangeService service) throws Exception {
		ItemView view = new ItemView(10);
		view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Ascending);
		view.setPropertySet(new PropertySet(BasePropertySet.IdOnly, ItemSchema.Subject, ItemSchema.DateTimeReceived));

		FindItemsResults<Item> findResults =
	    	service.findItems(WellKnownFolderName.Inbox,
	        	new SearchFilter.SearchFilterCollection(
					LogicalOperator.Or, new SearchFilter.ContainsSubstring(ItemSchema.Subject, "EWS"),
				new SearchFilter.ContainsSubstring(ItemSchema.Subject, "API")), view);

	    //MOOOOOOST IMPORTANT: load items properties, before
	    service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
		System.out.println("Total number of items found: " + findResults.getTotalCount());
		
		return findResults; 
	}
  
}