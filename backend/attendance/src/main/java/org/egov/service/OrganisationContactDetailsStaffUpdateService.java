package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import digit.models.coremodels.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.egov.web.models.Organisation.ContactDetails;
import org.egov.web.models.Organisation.OrgContactUpdateDiff;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffPermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrganisationContactDetailsStaffUpdateService {

    @Autowired
    private AttendanceRegisterService attendanceRegisterService;
    @Autowired
    private StaffService staffService;

    public void updateStaffPermissionsForContactDetails(OrgContactUpdateDiff orgContactUpdateDiff) {
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(orgContactUpdateDiff.getRequestInfo()).build();
        Set<AttendanceRegister> attendanceRegisterSet = new HashSet<>();
        Set<ContactDetails> oldContacts = orgContactUpdateDiff.getOldContacts();

        for(ContactDetails oldContact : oldContacts) {
            AttendanceRegisterSearchCriteria attendanceRegisterSearchCriteria =
                    AttendanceRegisterSearchCriteria.builder().staffId(oldContact.getId()).build();
            List<AttendanceRegister> attendanceRegisterList = attendanceRegisterService.searchAttendanceRegister(requestInfoWrapper, attendanceRegisterSearchCriteria);
            revokePermission(attendanceRegisterList, oldContact, orgContactUpdateDiff.getRequestInfo());
            attendanceRegisterSet.addAll(attendanceRegisterList);
        }

        Set<ContactDetails> newContacts = orgContactUpdateDiff.getNewContacts();
        grantPermission(attendanceRegisterSet, newContacts, orgContactUpdateDiff.getRequestInfo());
    }

    public void revokePermission(List<AttendanceRegister> attendanceRegisters, ContactDetails oldContact, RequestInfo requestInfo) {
        if(attendanceRegisters.isEmpty())
            return;
        List<StaffPermission> staffPermissionList = new ArrayList<>();
        for(AttendanceRegister attendanceRegister : attendanceRegisters) {
            String tenantId = attendanceRegister.getTenantId();
            StaffPermission staffPermission = StaffPermission.builder()
                    .tenantId(tenantId).registerId(attendanceRegister.getId()).userId(oldContact.getId()).build();
            staffPermissionList.add(staffPermission);
        }
        StaffPermissionRequest staffPermissionRequest = StaffPermissionRequest.builder()
                .requestInfo(requestInfo).staff(staffPermissionList).build();
        staffService.deleteAttendanceStaff(staffPermissionRequest);
    }

    public void grantPermission(Set<AttendanceRegister> attendanceRegisters, Set<ContactDetails> newContacts, RequestInfo requestInfo) {
        if(attendanceRegisters.isEmpty())
            return;
        List<StaffPermission> staffPermissionList = new ArrayList<>();
        for(AttendanceRegister attendanceRegister : attendanceRegisters) {
            String tenantId = attendanceRegister.getTenantId();
            for(ContactDetails newContact : newContacts) {
                StaffPermission staffPermission = StaffPermission.builder().tenantId(tenantId)
                        .registerId(attendanceRegister.getId()).userId(newContact.getId()).build();
                staffPermissionList.add(staffPermission);
            }
        }
        StaffPermissionRequest staffPermissionRequest = StaffPermissionRequest.builder()
                .requestInfo(requestInfo).staff(staffPermissionList).build();
        staffService.createAttendanceStaff(staffPermissionRequest);
    }

}
