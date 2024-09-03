/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) 2016  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.web.models.Hrms;

import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Validated
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Setter
@ToString
@Builder
public class Employee {

    private Long id;

    
    @Size(max = 1024)
    private String uuid;

    
    @Size(min = 1, max = 256)
    private String code;

    
    @Size(max = 250)
    private String employeeStatus;

    
    @NotNull
    @Size(max = 250)
    private String employeeType;

    private Long dateOfAppointment;

    @Valid
    @NotEmpty
    @Size(min = 1,max = 50)
    private List<Jurisdiction> jurisdictions = new ArrayList<>();


    @Valid
    private List<Assignment> assignments = new ArrayList<>();

    @Valid
    @Size(max=25)
    private List<ServiceHistory> serviceHistory = new ArrayList<>();


    private Boolean IsActive;

    @Valid
    @Size(max=25)
    private List<EducationalQualification> education = new ArrayList<>();

    @Valid
    @Size(max=25)
    private List<DepartmentalTest> tests = new ArrayList<>();

    
    @NotNull
    @Size(max = 250)
    private String tenantId;

    @Valid
    @Size(max=50)
    private List<EmployeeDocument> documents = new ArrayList<>();

    @Valid
    private List<DeactivationDetails> deactivationDetails = new ArrayList<>();

    private List<ReactivationDetails> reactivationDetails = new ArrayList<>();

    private AuditDetails auditDetails;

    private Boolean reActivateEmployee;
    
    @Valid
    @NotNull
    private User user;


}