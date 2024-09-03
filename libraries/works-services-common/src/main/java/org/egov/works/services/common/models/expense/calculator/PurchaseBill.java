package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.models.Document;
import org.egov.common.contract.models.Workflow;
import org.egov.works.services.common.models.expense.BillDetail;
import org.egov.works.services.common.models.expense.Party;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseBill {
	@JsonProperty("id")
	@Valid
	private String id;

	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId = null;

	@JsonProperty("invoiceDate")
	@Valid
	private Long invoiceDate;

	@JsonProperty("invoiceNumber")
	@Valid
	private String invoiceNumber;

	@JsonProperty("referenceId")
	@Size(min = 2, max = 128)
	private String referenceId;

	@JsonProperty("billNumber")
	private String billNumber;
	
	@JsonProperty("contractNumber")
	@Valid
	private String contractNumber;
	
	@JsonProperty("projectId")
	@Valid
	private String projectId;
	
	@JsonProperty("status")
	@Size(min = 2, max = 64)
	private String status;

	@JsonProperty("billDetails")
	@NotNull
	@Valid
	private List<BillDetail> billDetails;

	@JsonProperty("additionalDetails")
	private Object additionalDetails;

	@JsonProperty("auditDetails")

	@Valid
	private AuditDetails auditDetails;
	@JsonProperty("documents")
	@Valid
	private List<Document> documents = null;

	@JsonProperty("workflow")
	private Workflow workflow;

	@JsonProperty("payer")
	private Party party;

	/**
	 * Copied fields bill which are not available in this Purchase model
	 */
	@JsonProperty("billDate")
	@Valid
	private Long billDate;

	@JsonProperty("dueDate")
	@Valid
	private Long dueDate;

	@JsonProperty("totalAmount")
	@Valid
	@Builder.Default
	private BigDecimal totalAmount = BigDecimal.ZERO;

	@JsonProperty("totalPaidAmount")
	@Valid
	@Builder.Default
	private BigDecimal totalPaidAmount = BigDecimal.ZERO;

	@JsonProperty("businessService")
	@NotNull
	@Size(min = 2, max = 128)
	private String businessService;

	@JsonProperty("fromPeriod")
	@Valid
	private Long fromPeriod;
	@JsonProperty("toPeriod")
	@Valid
	private Long toPeriod;

	@JsonProperty("paymentStatus")
	@Size(min = 2, max = 64)
	private String paymentStatus;
	@JsonProperty("wfStatus")
	@Size(min = 2, max = 64)
	private String wfStatus;


	public PurchaseBill addDocumentsItem(Document documentsItem) {
		if (this.documents == null) {
			this.documents = new ArrayList<>();
		}
		this.documents.add(documentsItem);
		return this;
	}

	public PurchaseBill addBillDetailsItem(BillDetail billDetailsItem) {

		if (null == this.billDetails)
			this.billDetails = new ArrayList<>();

		this.billDetails.add(billDetailsItem);
		return this;
	}

}
