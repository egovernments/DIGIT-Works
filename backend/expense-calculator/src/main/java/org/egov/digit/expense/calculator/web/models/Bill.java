package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.ProcessInstance;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A Object which holds the info about the expense details
 */
@Schema(description = "A Object which holds the info about the expense details")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill {
	
	@JsonProperty("id")
	@Valid
	private String id;

	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId;

	@JsonProperty("billDate")
	@Valid
	private Long billDate;

	@JsonProperty("dueDate")
	@Valid
	private Long dueDate;

	@JsonProperty("netPayableAmount")
	@Valid
	private BigDecimal netPayableAmount;

	@JsonProperty("netPaidAmount")
	@Valid
	private BigDecimal netPaidAmount;

	@JsonProperty("businessservice")
	@NotNull
	@Size(min = 2, max = 64)
	private String businessService;

	@JsonProperty("referenceId")
	@Size(min = 2, max = 64)
	private String referenceId;

	@JsonProperty("fromPeriod")
	@Valid
	private Long fromPeriod;

	@JsonProperty("toPeriod")
	@Valid
	private Long toPeriod;
	
	@JsonProperty("paymentStatus")
	@Size(min = 2, max = 64)
	private String paymentStatus;

	@JsonProperty("status")
	@Size(min = 2, max = 64)
	private String status;

	@JsonProperty("payer")
	@NotNull
	@Valid
	private Party payer;

	@JsonProperty("billDetails")
	@NotNull
	@Valid
	private List<BillDetail> billDetails;

	@JsonProperty("additionalDetails")
	private Object additionalDetails;

	@JsonProperty("auditDetails")
	@Valid
	private AuditDetails auditDetails;
	
	@JsonProperty("workflow")
	private ProcessInstance workflow;

	public Bill addBillDetailsItem(BillDetail billDetailsItem) {
		
		if(null == this.billDetails)
			this.billDetails = new ArrayList<>();
		
		this.billDetails.add(billDetailsItem);
		return this;
	}

}
