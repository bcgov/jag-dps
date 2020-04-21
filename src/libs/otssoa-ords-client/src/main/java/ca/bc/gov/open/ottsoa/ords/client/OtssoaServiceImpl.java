package ca.bc.gov.open.ottsoa.ords.client;

import ca.bc.gov.open.ottsoa.ords.client.api.OtssoaApi;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.CreateObjectPayload;
import ca.bc.gov.open.ottsoa.ords.client.api.model.CreatePackagePayload;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;
import ca.bc.gov.open.ottsoa.ords.client.models.CreateObjectRequest;
import ca.bc.gov.open.ottsoa.ords.client.models.CreatePackageRequest;
import org.apache.commons.lang3.StringUtils;

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

    @Override
    public DefaultResponse CreateObject(CreateObjectRequest createObjectRequest) throws ApiException {

        CreateObjectPayload payload = new CreateObjectPayload();
        payload.setCompletionDate(formatDate(createObjectRequest.getCompletionDate()));
        payload.setClientName(createObjectRequest.getClientName());
        payload.setCaseResults(createObjectRequest.getCaseResults());
        payload.setActionUser(createObjectRequest.getActionUser());
        payload.setActionSystem(createObjectRequest.getActionSystem());
        payload.setActionMethod(createObjectRequest.getActionMethod());
        payload.setCaseUpdate(createObjectRequest.getCaseUpdate());
        payload.setClientNumber(StringUtils.isBlank(createObjectRequest.getClientNumber()) ? Keys.DEFAULT_CLIENT_NUMBER : createObjectRequest.getClientNumber());
        payload.setContentId(createObjectRequest.getContentId());
        payload.setContentType(createObjectRequest.getContentType());
        payload.setImageUpload(createObjectRequest.getImageUpload());
        payload.setImportGuid(createObjectRequest.getImportGuid().toString());
        payload.setPackageFormatType(createObjectRequest.getPackageFormatType());

        return otssoaApi.createObjectPost(payload);
    }


    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Keys.ORDS_DATE_PATTERN);
        return sdf.format(date);
    }

}
