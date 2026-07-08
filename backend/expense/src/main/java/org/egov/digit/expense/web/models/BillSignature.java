package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * A sign-off record captured when a payment actor (editor / reviewer / approver)
 * moves a bill through the PAYMENTS workflow. The signature image itself lives in
 * egov-filestore; only the fileStoreId is stored here.
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillSignature {

	@JsonProperty("id")
	private String id;

	@JsonProperty("printedName")
	@Size(max = 256)
	private String printedName;

	@JsonProperty("fileStoreId")
	@Size(max = 256)
	private String fileStoreId;

	/** Workflow action this signature was captured for, e.g. SEND_FOR_APPROVAL, PAYMENT_INITIATION. */
	@JsonProperty("action")
	@Size(max = 64)
	private String action;

	/** Payment role of the signing user, e.g. PAYMENT_EDITOR, PAYMENT_REVIEWER, PAYMENT_APPROVER. */
	@JsonProperty("role")
	@Size(max = 128)
	private String role;

	/** uuid of the signing user account. */
	@JsonProperty("signedBy")
	private String signedBy;

	@JsonProperty("signedTime")
	private Long signedTime;
}
