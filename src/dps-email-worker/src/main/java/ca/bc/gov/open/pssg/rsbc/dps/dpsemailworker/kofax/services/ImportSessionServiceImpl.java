package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration.TenantProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.KofaxProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.*;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ImportSessionServiceImpl implements ImportSessionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final KofaxProperties kofaxProperties;

    private final TenantProperties tenantProperties;

    private final JAXBContext kofaxImportSession;

    public ImportSessionServiceImpl(KofaxProperties kofaxProperties, TenantProperties tenantProperties,
                                    JAXBContext kofaxImportSession) {
        this.kofaxProperties = kofaxProperties;
        this.tenantProperties = tenantProperties;
        this.kofaxImportSession = kofaxImportSession;
    }

    @Override
    public ImportSession generateImportSession(DpsMetadata dpsMetadata) {
        return getImportSession(dpsMetadata);
    }

    @Override
    public byte[] convertToXmlBytes(ImportSession importSession) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Marshaller marshaller = kofaxImportSession.createMarshaller();
            marshaller.marshal(importSession, baos);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();

    }

    private ImportSession getImportSession(DpsMetadata dpsMetadata) {

        Batch batch = getBatch(dpsMetadata);

        batch.getPages().addPage(getPage(dpsMetadata));

        batch.getBatchFields().addBatchField(getDateBatchField(dpsMetadata));

        batch.getBatchFields().addBatchField(getProgramTypeBatchField(dpsMetadata));
        batch.getBatchFields().addBatchField(getFaxReceivedDateField(dpsMetadata));
        batch.getBatchFields().addBatchField(getOrininatingNumber(dpsMetadata));
        batch.getBatchFields().addBatchField(getImportIdField(dpsMetadata));

        ImportSession session = new ImportSession(kofaxProperties.getUserId(), kofaxProperties.getPassword(), null, null);

        session.getBatches().addBatch(batch);

        return session;
    }

    private Batch getBatch(DpsMetadata dpsMetadata) {
        return new Batch.Builder()
                .withInputChannel(dpsMetadata.getInboundChannelType())
                .withBatchClassName(tenantProperties.getName())
                .withEnableAutomaticSeparationAndFormID(kofaxProperties.getEnableAutoSeparationAndFormid())
                .withRelativeImageFilePath(kofaxProperties.getRelativeImageFilePath())
                .build();
    }

    private Page getPage(DpsMetadata dpsMetadata) {
        return new Page.Builder()
                .withimportFileName(dpsMetadata.getFileInfo().getName())
                .withoriginalFileName(dpsMetadata.getFileInfo().getName())
                .build();
    }

    private BatchField getDateBatchField(DpsMetadata dpsMetadata) {
        return new BatchField(kofaxProperties.getBatchFieldImportDate(),
                getTimeStamp(kofaxProperties.getXmlDatePattern()));
    }

    private BatchField getProgramTypeBatchField(DpsMetadata dpsMetadata) {
        return new BatchField(kofaxProperties.getBatchFieldProgramType(), tenantProperties.getName());
    }

    private BatchField getFaxReceivedDateField(DpsMetadata dpsMetadata) {
        return new BatchField(kofaxProperties.getBatchFieldFaxReceiveDate(), formatDate(dpsMetadata.getReceivedDate(), "yyyy-MM-dd'T'H:mm:ss'Z'"));
    }

    private BatchField getOrininatingNumber(DpsMetadata dpsMetadata) {
        return new BatchField(kofaxProperties.getBatchFieldOrigNum(), dpsMetadata.getOriginatingNumber());
    }

    private BatchField getImportIdField(DpsMetadata dpsMetadata) {
        return new BatchField(kofaxProperties.getBatchFieldImportId(), dpsMetadata.getTransactionId().toString());
    }

    private String getTimeStamp(String pattern) {
        Calendar calendar = Calendar.getInstance();
        return formatDate(calendar.getTime(), pattern);
    }

    private String formatDate(Date date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }


}
