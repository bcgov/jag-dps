package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcms;

import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.DpsValidationServiceConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetValidOpenDFCMCase")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetValidOpenDFCMCase {

    @JsonProperty("int")
    private String result;

    private String caseDesc;

    public String getResult() {
        return result;
    }

    public String getCaseDesc() {
        return caseDesc;
    }

    public GetValidOpenDFCMCase(String result) {
        this.result = result;
    }


    public GetValidOpenDFCMCase(String result, String caseDesc) {
        this(result);
        this.caseDesc = caseDesc;
    }

    public static GetValidOpenDFCMCase ErrorResponse() {
        return new GetValidOpenDFCMCase(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE_CD);
    }

    public static GetValidOpenDFCMCase SuccessResponse(String caseSequenceNumber, String caseDesc) {
        return new GetValidOpenDFCMCase(caseSequenceNumber, caseDesc);
    }

}
