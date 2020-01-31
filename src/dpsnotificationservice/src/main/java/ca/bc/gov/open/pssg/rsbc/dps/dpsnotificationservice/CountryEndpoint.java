package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.Country;
import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.GetCountryRequest;
import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.GetCountryResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class CountryEndpoint {


    @PayloadRoot(namespace = Keys.NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getOutputNotification(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        Country country = new Country();
        country.setRespCode("0");
        country.setRespMsg("success");
        response.setCountry(country);
        return response;
    }
}