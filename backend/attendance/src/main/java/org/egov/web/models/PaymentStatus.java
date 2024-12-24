package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus {

	APPROVED("APPROVED"),

	PENDINGFORAPPROVAL("PENDINGFORAPPROVAL");

	private final String value;

	PaymentStatus(String value){
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static PaymentStatus fromValue(String text) {
		for (PaymentStatus b : PaymentStatus.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}
}
