package org.egov.works.models;

import lombok.ToString;

import java.util.Map;

@ToString
public class SMSRequest {
    private String mobileNumber;
    private String message;
    private Map<String,Object> additionalFields;

    public static SMSRequestBuilder builder() {
        return new SMSRequestBuilder();
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public String getMessage() {
        return this.message;
    }

    public Map<String,Object> getAdditionalFields(){ return this. additionalFields;}

    public SMSRequest(String mobileNumber, String message,Map<String,Object> additionalFields) {
        this.mobileNumber = mobileNumber;
        this.message = message;
        this.additionalFields = additionalFields;
    }

    public SMSRequest() {
    }

    public String toString() {
        return "SMSRequest(mobileNumber=" + this.getMobileNumber() + ", message=" + this.getMessage() + ")";
    }

    public static class SMSRequestBuilder {
        private String mobileNumber;
        private String message;

        private Map<String,Object> additionalFields;

        SMSRequestBuilder() {
        }

        public SMSRequestBuilder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public SMSRequestBuilder message(String message) {
            this.message = message;
            return this;
        }

        public SMSRequestBuilder additionalFields(Map<String,Object> additionalFields) {
            this.additionalFields = additionalFields;
            return this;
        }

        public SMSRequest build() {
            return new SMSRequest(this.mobileNumber, this.message,this.additionalFields);
        }

        public String toString() {
            return "SMSRequest.SMSRequestBuilder(mobileNumber=" + this.mobileNumber + ", message=" + this.message + ", additionalFields=" + this.additionalFields + ")";
        }
    }
}