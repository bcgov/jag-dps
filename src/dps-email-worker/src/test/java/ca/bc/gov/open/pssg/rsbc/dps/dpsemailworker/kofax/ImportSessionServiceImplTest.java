package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.Fakes;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration.TenantProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.ImportSession;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionServiceImpl;
import ca.bc.gov.open.pssg.rsbc.models.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImportSessionServiceImplTest {

    private static final String INBOUND_CHANNEL_TYPE = "ICP";
    private static final String CASE_1 = "case1";
    private static final String NAME = "case1.ext";
    private static final String CONTENT_TYPE = "application/text";
    private static final String PASSWORD = "changemepass";
    private static final String USER_ID = "changemeuser";
    private static final String TENANT = "TEST";
    private ImportSessionServiceImpl sut;

    @BeforeEach
    public void setUp() {

        KofaxProperties kofaxProperties = new KofaxProperties();
        kofaxProperties.setBatchFieldFaxReceiveDate("FaxReceivedDate");
        kofaxProperties.setBatchFieldImportDate("ImportDate");
        kofaxProperties.setBatchFieldImportId("ImportID");
        kofaxProperties.setBatchFieldOrigNum("OriginatingNumber");
        kofaxProperties.setBatchFieldProgramType("ProgramType");
        kofaxProperties.setFileNameDatePattern("");
        kofaxProperties.setEnableAutoSeparationAndFormid("1");
        kofaxProperties.setRelativeImageFilePath(".");
        kofaxProperties.setPassword(PASSWORD);
        kofaxProperties.setUserId(USER_ID);
        kofaxProperties.setXmlDatePattern("yyyy-MM-dd");

        TenantProperties tenantProperties = new TenantProperties();
        tenantProperties.setName(TENANT);

        sut = new ImportSessionServiceImpl(kofaxProperties, tenantProperties, getImportSessionJaxbContext(kofaxProperties, tenantProperties));

    }

    @Test
    public void withValidDataShouldSendImageAndXml() {

        DpsFileInfo fileInfo = new DpsFileInfo(CASE_1, NAME, CONTENT_TYPE);

        DpsMetadata metadata = new DpsMetadata.Builder()
                .withInboundChannelType(INBOUND_CHANNEL_TYPE)
                .withFileInfo(fileInfo)
                .withRecvdate(getDate())
                .withOriginatingNumber("250387-481")
                .build();

        String patternValue = "<\\?xml version=\"1\\.0\" encoding=\"UTF-8\" standalone=\"yes\"\\?>" +
                "<ImportSession UserID=\"" + USER_ID + "\" Password=\"" + PASSWORD + "\">" +
                "<Batches>" +
                "<Batch " +
                "Name=\"" + INBOUND_CHANNEL_TYPE + "-\\d{4}-\\d{2}-\\d{2}-\\d{2}-\\d{1,2}-\\d{1,2}-\\d{3}\" " +
                "BatchClassName=\"" + TENANT + "\" " +
                "EnableAutomaticSeparationAndFormID=\"1\" " +
                "RelativeImageFilePath=\"\\.\">" +
                "<BatchFields>" +
                "<BatchField Name=\"ImportDate\" Value=\"\\d{4}-\\d{2}-\\d{2}\"\\/>" +
                "<BatchField Name=\"ProgramType\" Value=\"" + TENANT + "\"\\/>" +
                "<BatchField Name=\"FaxReceivedDate\" Value=\"2020-02-01T2:30:26Z\"\\/>" +
                "<BatchField Name=\"OriginatingNumber\" Value=\"250387-481\"\\/>" +
                "<BatchField " +
                "Name=\"ImportID\" " +
                "Value=\"" + metadata.getTransactionId().toString() + "\"\\/><\\/BatchFields>" +
                "<Pages>" +
                "<Page ImportFileName=\"" + NAME + "\" OriginalFileName=\"" + NAME + "\"\\/>" +
                "<\\/Pages>" +
                "<\\/Batch>" +
                "<\\/Batches>" +
                "<\\/ImportSession>";

        Pattern pattern = Pattern.compile(patternValue);

        ImportSession session = sut.generateImportSession(metadata);

        String result = new String(sut.convertToXmlBytes(session));

        if (!pattern.matcher(result).matches()) {
            // this will make the test fail and show the diff with the regex and the value
            Assertions.assertEquals(
                    patternValue
                            .replace("\\", "")
                            .replace("d{4}-d{2}-d{2}-d{2}-d{1,2}-d{1,2}-d{3}", "yyyy-mm-DD-HH-MM-ss-SSS")
                            .replace("d{4}-d{2}-d{2}", "yyyy-mm-DD")
                            .replace(">", ">\n"), result.replace(">", ">\n"));

        }

    }

    @Test
    public void withValidInputStreamShouldConvertToImportSession() {


        ImportSession actual = sut.convertToImportSession(Fakes.getImportSessionInputStream());

        Assertions.assertEquals("test", actual.getUserID());
        Assertions.assertEquals("test", actual.getPassword());

    }

    private JAXBContext getImportSessionJaxbContext(KofaxProperties kofaxProperties, TenantProperties tenantProperties) {

        KofaxConfig config = new KofaxConfig(kofaxProperties, tenantProperties);

        JAXBContext result = null;

        try {
            result = config.kofaxImportSessionJaxbContext();
        } catch (JAXBException e) {
            Assertions.fail("Configuration did throw an exception");
        }

        return result;
    }

    private Date getDate() {
        Calendar calendar = new GregorianCalendar(2020, 1, 1, 2, 30, 26);
        return calendar.getTime();
    }

}
