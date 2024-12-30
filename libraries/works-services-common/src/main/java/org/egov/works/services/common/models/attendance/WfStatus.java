package org.egov.works.services.common.models.attendance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WfStatus {

	APPROVED("APPROVED"),

	PENDINGFORAPPROVAL("PENDINGFORAPPROVAL");

	private final String value;

	WfStatus(String value){
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static WfStatus fromValue(String text) {
		for (WfStatus b : WfStatus.values()) {
			if (String.valueOf(b.value).equalsIgnoreCase(text)) {
				return b;
			}
		}
		return null;
	}
}