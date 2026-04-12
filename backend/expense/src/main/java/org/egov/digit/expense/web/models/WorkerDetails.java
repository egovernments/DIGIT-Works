package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkerDetails {

	private String workerId;

	private String paymentProvider;

	private String payeeName;

	private String payeePhoneNumber;

	private String bankAccount;

	private String bankCode;

	private String beneficiaryCode;
}
