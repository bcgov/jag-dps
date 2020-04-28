package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Fakes {

    public static InputStream getImportSessionInputStream() {

        String importSessionXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ImportSession " +
                "UserID=\"test\" Password=\"test\"><Batches><Batch " +
                "Name=\"FAX-2020-04-17-11-24-42-981\" BatchClassName=\"SPD-CRC\" " +
                "EnableAutomaticSeparationAndFormID=\"1\" RelativeImageFilePath=\".\"><BatchFields><BatchField " +
                "Name=\"ImportDate\" Value=\"2020-04-17\"/><BatchField Name=\"ProgramType\" " +
                "Value=\"SPD-CRC\"/><BatchField Name=\"FaxReceivedDate\" Value=\"2018-09-12T11:26:35Z\"/><BatchField " +
                "Name=\"OriginatingNumber\" Value=\"250-356-5987\"/><BatchField Name=\"ImportID\" " +
                "Value=\"0308eec8-b6c1-44ab-97f9-8ee313557e6c\"/></BatchFields><Pages><Page " +
                "ImportFileName=\"Fax-Sep-12-2018-11-26-31-9405.tif\" " +
                "OriginalFileName=\"Fax-Sep-12-2018-11-26-31-9405.tif\"/></Pages></Batch></Batches></ImportSession>";


        return new ByteArrayInputStream(importSessionXml.getBytes());

    }


}
