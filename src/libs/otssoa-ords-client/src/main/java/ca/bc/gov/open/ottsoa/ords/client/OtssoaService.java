package ca.bc.gov.open.ottsoa.ords.client;

import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;

public interface OtssoaService {

    DefaultResponse CreatePackage(CreatePackageRequest createPackageRequest) throws ApiException;

}
