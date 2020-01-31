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
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        Country country = new Country();
        country.setCapital("yo");
        response.setCountry(country);
        return response;
    }
}