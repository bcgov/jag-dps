package ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.PaymentServiceConstants.BamboraTransType;

/**
 * SinglePaymentResponse object
 *
 * @author smillar
 */
public class SinglePaymentRequest {

    public static class Builder {

        private BamboraTransType transType;
        private String invoiceNumber;
        private Double totalItemsAmount;
        private String approvedPage;
        private String declinedPage;
        private String errorPage;
        private String ref1;
        private String ref2;
        private String ref3;


        public Builder withBamboraTransType(BamboraTransType transType) {
            this.transType = transType;
            return this;
        }

        public Builder withInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
            return this;
        }

        public Builder withTotalItemsAmount(Double totalItemsAmount) {
            this.totalItemsAmount = totalItemsAmount;
            return this;
        }

        public Builder withApprovedPage(String approvedPage) {
            this.approvedPage = approvedPage;
            return this;
        }

        public Builder withDeclinedPage(String declinedPage) {
            this.declinedPage = declinedPage;
            return this;
        }

        public Builder withErrorPage(String errorPage) {
            this.errorPage = errorPage;
            return this;
        }

        public Builder withRef1(String ref1) {
            this.ref1 = ref1;
            return this;
        }

        public Builder withRef2(String ref2) {
            this.ref2 = ref2;
            return this;
        }

        public Builder withRef3(String ref3) {
            this.ref3 = ref3;
            return this;
        }

        public SinglePaymentRequest build() {
            SinglePaymentRequest result = new SinglePaymentRequest();
            result.transType = transType;
            result.invoiceNumber = invoiceNumber;
            result.totalItemsAmount = totalItemsAmount;
            result.approvedPage = approvedPage;
            result.declinedPage = declinedPage;
            result.errorPage = errorPage;
            result.ref1 = ref1;
            result.ref2 = ref2;
            result.ref3 = ref3;
            return result;
        }
    }


    private BamboraTransType transType;
    private String invoiceNumber;
    private Double totalItemsAmount;
    private String approvedPage;
    private String declinedPage;
    private String errorPage;
    private String ref1;
    private String ref2;
    private String ref3;

    public BamboraTransType getTransType() {
        return transType;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Double getTotalItemsAmount() {
        return totalItemsAmount;
    }

    public String getApprovedPage() {
        return approvedPage;
    }

    public String getDeclinedPage() {
        return declinedPage;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public String getRef1() {
        return ref1;
    }

    public String getRef2() {
        return ref2;
    }

    public String getRef3() {
        return ref3;
    }
}
