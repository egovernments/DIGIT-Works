package org.egov.wms.web.model.Job;

public enum JobStatus {
    INITIATED("INITIATED"),
    INPROGRESS("INPROGRESS"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    private String value;

    JobStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static JobStatus fromValue(String text) {
        for (JobStatus b : JobStatus.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
