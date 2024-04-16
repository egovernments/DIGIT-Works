package org.egov.works.services.common.models.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


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
	private Set<String> ids;

	@JsonProperty("businessService")
	@Size(min = 2, max = 64)
	private String businessService;

	@JsonProperty("referenceIds")
	private Set<String> referenceIds;

	@JsonProperty("billNumbers")
	private Set<String> billNumbers;

	@JsonProperty("status")
	private String status;
	
	@JsonProperty("statusNot")
	private String statusNot;

	@JsonProperty("isPaymentStatusNull")
	private Boolean isPaymentStatusNull;

	public BillCriteria addIdsItem(String idsItem) {
		if (this.ids == null) {
			this.ids = new HashSet<>();
		}
		this.ids.add(idsItem);
		return this;
	}

	public BillCriteria addReferenceIdItem(String referenceIdItem) {
		if (this.referenceIds == null) {
			this.referenceIds = new HashSet<>();
		}
		this.referenceIds.add(referenceIdItem);
		return this;
	}

}
