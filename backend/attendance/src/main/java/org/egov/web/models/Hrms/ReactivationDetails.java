package org.egov.web.models.Hrms;

import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Validated
@EqualsAndHashCode(exclude = {"auditDetails"})
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@ToString
@Builder
public class ReactivationDetails {

	
	private String id;

	
	@NotNull
	private String reasonForReactivation;

	
	private String orderNo;

	
	private String remarks;

	@NotNull
	private Long effectiveFrom;

	
	private String tenantId;

	private AuditDetails auditDetails;




}


