package org.egov.digit.expense.web.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillCriteria
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillCriteria {
	
	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 2, max = 64)
	private String tenantId;

	@JsonProperty("ids")
	private List<String> ids;

	@JsonProperty("businessService")
	@Size(min = 2, max = 64)
	private String businessService;

	@JsonProperty("referenceId")
	private List<String> referenceId;

	@JsonProperty("isActive")
	private Boolean isActive;

	public BillCriteria addIdsItem(String idsItem) {
		if (this.ids == null) {
			this.ids = new ArrayList<>();
		}
		this.ids.add(idsItem);
		return this;
	}

	public BillCriteria addReferenceIdItem(String referenceIdItem) {
		if (this.referenceId == null) {
			this.referenceId = new ArrayList<>();
		}
		this.referenceId.add(referenceIdItem);
		return this;
	}

}
