package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcsm;

import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.DpsValidationServiceConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetValidOpenDFCMCase")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetValidOpenDFCMCase {

    @JsonProperty("int")
    private int result;

    private String caseDesc;

    public int getResult() {
        return result;
    }

    public String getCaseDesc() {
        return caseDesc;
    }

    public GetValidOpenDFCMCase(int result) {
        this.result = result;
    }


    public GetValidOpenDFCMCase(int result, String caseDesc) {
        this(result);
        this.caseDesc = caseDesc;
    }

    public static GetValidOpenDFCMCase ErrorResponse() {
        return new GetValidOpenDFCMCase(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE_CD);
    }

    public static GetValidOpenDFCMCase SuccessResponse(String caseDesc) {
        return new GetValidOpenDFCMCase(2, caseDesc);
    }

}
