package org.egov.digit.expense.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * status of the Property
 */
public enum Status {

	ACTIVE ("ACTIVE"),

	INACTIVE ("INACTIVE"),

	INWORKFLOW ("INWORKFLOW"),
	
	CANCELLED ("CANCELLED"),
	
	REJECTED ("REJECTED"),

    PENDING_VERIFICATION ("PENDING_VERIFICATION"),

    VERIFICATION_FAILED ("VERIFICATION_FAILED"),

    PARTIALLY_VERIFIED ("PARTIALLY_VERIFIED"),

    FULLY_VERIFIED ("FULLY_VERIFIED"),

    PENDING_EDIT ("PENDING_EDIT"),

    EDITED ("EDITED"),

    VERIFIED ("VERIFIED"),

    PARTIALLY_PAID("PARTIALLY_PAID"),

    PAYMENT_FAILED ("PAYMENT_FAILED"),

    FULLY_PAID ("FULLY_PAID"),

    PAID ("PAID"),

    IN_PROGRESS ("IN_PROGRESS"),

    DONE ("DONE");

	private String value;

  Status(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static Status fromValue(String text) {
    for (Status b : Status.values()) {
      if (String.valueOf(b.value).equalsIgnoreCase(text)) {
        return b;
      }
    }
    return null;
  }
}
