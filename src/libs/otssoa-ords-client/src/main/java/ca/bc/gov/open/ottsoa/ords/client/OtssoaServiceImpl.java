package ca.bc.gov.open.ottsoa.ords.client;

import ca.bc.gov.open.ottsoa.ords.client.api.OtssoaApi;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.CreatePackagePayload;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OtssoaServiceImpl implements OtssoaService {

    private final OtssoaApi otssoaApi;

    public OtssoaServiceImpl(OtssoaApi otssoaApi) {
        this.otssoaApi = otssoaApi;
    }

    public DefaultResponse CreatePackage(CreatePackageRequest createPackageRequest) throws ApiException {

        CreatePackagePayload payload = new CreatePackagePayload();
        payload.setPageCount(Integer.toString(createPackageRequest.getPageCount()));
        payload.setRecordCount(Integer.toString(createPackageRequest.getRecordCount()));
        payload.setProgramType(createPackageRequest.getProgramType());
        payload.setFilename(createPackageRequest.getFilename());
        payload.setSource(createPackageRequest.getSource());
        payload.setRecipient(createPackageRequest.getRecipient());
        payload.setReceivedDate(formatDate(createPackageRequest.getReceivedDate()));
        payload.setImportGuid(createPackageRequest.getImportGuid().toString());
        payload.setBusinessArea(createPackageRequest.getBusinessArea());
        payload.setFormatType(createPackageRequest.getFormatType());
        return otssoaApi.createPackagePost(payload);

    }


    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Keys.ORDS_DATE_PATTERN);
        return sdf.format(date);
    }

}
