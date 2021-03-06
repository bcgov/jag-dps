package ca.bc.gov.open.ottsoa.ords.client;

import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;
import ca.bc.gov.open.ottsoa.ords.client.models.CreateObjectRequest;
import ca.bc.gov.open.ottsoa.ords.client.models.CreatePackageRequest;

public interface OtssoaService {

    DefaultResponse createPackage(CreatePackageRequest createPackageRequest) throws ApiException;

    DefaultResponse createObject(CreateObjectRequest createObjectRequest) throws ApiException;

}
