package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import java.util.Date;

public class DpsEmailContent {

    public static class Builder{

        private String phoneNumber;
        private Date date;
        private Integer pageCount;
        private String jobId;

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder withPageCount(Integer pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Builder withJobId(String jobId) {
            this.jobId = jobId;
            return  this;
        }

        public DpsEmailContent build() {
            DpsEmailContent dpsEmailContent = new DpsEmailContent();
            dpsEmailContent.date = this.date;
            dpsEmailContent.jobId = this.jobId;
            dpsEmailContent.PageCount = this.pageCount;
            dpsEmailContent.phoneNumer = this.phoneNumber;
            return dpsEmailContent;
        }

    }


    private String phoneNumer;
    private Date date;
    private Integer PageCount;
    private String jobId;

    public String getPhoneNumer() {
        return phoneNumer;
    }

    public Date getDate() {
        return date;
    }

    public Integer getPageCount() {
        return PageCount;
    }

    public String getJobId() {
        return jobId;
    }
}
