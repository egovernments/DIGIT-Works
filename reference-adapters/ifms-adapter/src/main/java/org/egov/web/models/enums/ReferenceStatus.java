package org.egov.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotNull;

/**
 * enum value for the payment status
 */
public enum ReferenceStatus {

	PAYMENT_INITIATED("PAYMENT_INITIATED"),

	PAYMENT_PARTIAL("PAYMENT_PARTIAL"),

	PAYMENT_FAILED("PAYMENT_FAILED"),

	PAYMENT_SUCCESS("PAYMENT_SUCCESS"),

	PAYMENT_ERROR_PROCESSING_PAYMENT("PAYMENT_ERROR_PROCESSING_PAYMENT"),

	PAYMENT_INSUFFICIENT_FUNDS("PAYMENT_INSUFFICIENT_FUNDS"),

	PAYMENT_SERVER_UNREACHABLE("PAYMENT_SERVER_UNREACHABLE"),

	PAYMENT_DECLINED("PAYMENT_DECLINED");


	private String value;

	ReferenceStatus(String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	@NotNull
	public static ReferenceStatus fromValue(String text) {
		for (ReferenceStatus b : ReferenceStatus.values()) {
			if (String.valueOf(b.value).equalsIgnoreCase(text)) {
				return b;
			}
		}
		return null;
	}
}
