package ca.bc.gov.open.pssg.rsbc.spd.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.files.FileInfo;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.dps.spd.notification.worker.generated.models.Data;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

/**
 * Comsumes messages pushed to the CRRP Queue
 *
 * @author alexjoybc@github
 */
@Component
public class OutputNotificationConsumer {

    private static final String IMAGE_EXTENSION = "PDF";
    private static final int SUCCESS_CODE = 0;
    private static final String DPS_FILE_ID_KEY = "dps.fileId";
    private static final String DPS_BUSINESS_AREA_CD_KEY = "dps.businessAreaCd";

    private final FileService fileService;
    private final SftpProperties sftpProperties;
    private final DocumentService documentService;
    private final JAXBContext kofaxOutputMetadataContext;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OutputNotificationConsumer(FileService fileService,
                                      SftpProperties sftpProperties,
                                      DocumentService documentService,
                                      @Qualifier("kofaxOutputMetadataContext") JAXBContext kofaxOutputMetadataContext) {
        this.fileService = fileService;
        this.sftpProperties = sftpProperties;
        this.documentService = documentService;
        this.kofaxOutputMetadataContext = kofaxOutputMetadataContext;
    }

    @RabbitListener(queues = Keys.CRRP_QUEUE_NAME)
    public void receiveMessage(OutputNotificationMessage message) {

        logger.info("received new {}", message);

        MDC.put(DPS_FILE_ID_KEY, message.getFileId());
        MDC.put(DPS_BUSINESS_AREA_CD_KEY, message.getBusinessAreaCd());

        FileInfo fileInfo = new FileInfo(message.getFileId(), IMAGE_EXTENSION, sftpProperties.getRemoteLocation());

        try {

            logger.debug("attempting to download file [{}]", fileInfo.getMetaDataReleaseFileName());
            String metadata = getMetadata(fileInfo);
            logger.info("successfully downloaded file [{}]", fileInfo.getMetaDataReleaseFileName());

            Data parsedData = unmarshallMetadataXml(metadata);
            Data.DocumentData documentData = parsedData.getDocumentData();

            DpsDocumentRequestBody documentRequestBody = new DpsDocumentRequestBody(sftpProperties.getHost(),
                    fileInfo.getImageReleaseFileName());

            DpsDocumentResponse documentResponse = documentService.dpsDocument(documentRequestBody);

            if (documentResponse.getRespCode() == SUCCESS_CODE) {
                logger.info("success: {} with {}", documentResponse, fileInfo);

                DpsDataIntoFigaroRequestBody dpsDataIntoFigaroRequestBody = new DpsDataIntoFigaroRequestBody.Builder()
                        .withScheduleType(documentData.getPvScheduleType())
                        .withJurisdictionType(documentData.getPvJurisdictionType())
                        .withProcessingStream(documentData.getPvProcessingStream())
                        .withApplicationCategory(documentData.getPvApplicationCategory())
                        .withPaymentMethod(documentData.getPvApplicationPaymentMethod())
                        .withNonFinRejectReason(documentData.getPvApplicationNonFinRejectRsn())
                        .withApplicationSignedYn(documentData.getPvApplicationSignedYN())
                        .withApplicationSignedDate(documentData.getPvApplicationSignedDate())
                        .withApplicationGuardianSignedYn(documentData.getPvApplicationGuardianSignedYN())
                        .withApplicationPaymentId(documentData.getPvApplicationPaymentId())
                        .withApplicationIncompleteReason(documentData.getPvApplicationIncompleteReason())
                        .withApplicationValidateUsername(documentData.getPvValidationUser())
                        .withApplicationDocumentGuid(documentResponse.getGuid())
                        .withApplPartyId(documentData.getPnApplPartyId())
                        .withApplSurname(documentData.getPvApplSurname())
                        .withApplFirstName(documentData.getPvApplFirstName())
                        .withApplSecondName(documentData.getPvApplSecondName())
                        .withApplBirthDate(documentData.getPvApplBirthDate())
                        .withApplGender(documentData.getPvApplGenderTxt())
                        .withApplBirthPlace(documentData.getPvApplBirthPlaceTxt())
                        .withApplAddlSurname1(documentData.getPvApplAddlSurname1())
                        .withApplAddlFirstName1(documentData.getPvApplAddlFirstName1())
                        .withApplAddlSecondName1(documentData.getPvApplAddlSecondName1())
                        .withApplAddlSurname2(documentData.getPvApplAddlSurname2())
                        .withApplAddlFirstName2(documentData.getPvApplAddlFirstName2())
                        .withApplAddlSecondName2(documentData.getPvApplAddlSecondName2())
                        .withApplAddlSurname3(documentData.getPvApplAddlSurname3())
                        .withApplAddlFirstName3(documentData.getPvApplAddlFirstName3())
                        .withApplAddlSecondName3(documentData.getPvApplAddlSecondName3())
                        .withApplStreetAddress(documentData.getPvApplStreetAddress())
                        .withApplCity(documentData.getPvApplCityNm())
                        .withApplProvince(documentData.getPvApplProvinceNm())
                        .withApplCountry(documentData.getPvApplCountryNm())
                        .withApplPostalCode(documentData.getPvApplPostalCode())
                        .withApplDriversLicence(documentData.getPvApplDriversLicence())
                        .withApplPhoneNumber(documentData.getPvApplPhoneNumber())
                        .withApplEmailAddress(documentData.getPvApplEmailAddress())
                        .withApplOrgPartyId(documentData.getPnOrgPartyId())
                        .withApplOrgFacilityPartyId(documentData.getPnOrgFacilityPartyId())
                        .withApplOrgFacilityName(documentData.getPvOrgFacilityName())
                        .withApplOrgContactPartyId(documentData.getPnOrgContactPartyId())
                        .build();

                DpsDataIntoFigaroResponse figaroResponse =
                        documentService.dpsDataIntoFigaro(dpsDataIntoFigaroRequestBody);

                if (figaroResponse.getRespCode() == SUCCESS_CODE) {
                    logger.info("success: {} with {}", figaroResponse, fileInfo);
                    fileService.moveFilesToArchive(fileInfo);
                } else {
                    logger.error("error: {} with {}", figaroResponse, fileInfo);
                    fileService.moveFilesToError(fileInfo);
                }
            } else {
                logger.error("error: {} with {}", documentResponse, fileInfo);
                fileService.moveFilesToError(fileInfo);
            }

        } catch (IOException | JAXBException e) {
            logger.error("{} while processing file id [{}]: ", e.getClass().getSimpleName(), fileInfo.getFileId(), e);
            fileService.moveFilesToError(fileInfo);
        } catch (DpsSftpException e) {
            logger.error("{} while processing file id [{}]: ", e.getClass().getSimpleName(), fileInfo.getFileId(), e);
        } finally {
            MDC.remove(DPS_FILE_ID_KEY);
            MDC.remove(DPS_BUSINESS_AREA_CD_KEY);
        }
    }

    private String getMetadata(FileInfo fileInfo) throws IOException {
        logger.debug("attempting get file metadata");
        InputStream is = fileService.getMetadataFileContent(fileInfo);
        return IOUtils.toString(is, StandardCharsets.UTF_8.name());
    }

    private Data unmarshallMetadataXml(String content) throws JAXBException {
        logger.debug("attempting to serialize file");
        Unmarshaller unmarshaller = this.kofaxOutputMetadataContext.createUnmarshaller();
        return (Data) unmarshaller.unmarshal(new StringReader(content));
    }
}
