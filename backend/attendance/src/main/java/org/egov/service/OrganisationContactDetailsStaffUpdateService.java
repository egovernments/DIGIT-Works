package org.egov.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.egov.web.models.Organisation.ContactDetails;
import org.egov.web.models.Organisation.OrgContactUpdateDiff;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffPermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class OrganisationContactDetailsStaffUpdateService {

    @Autowired
    private AttendanceRegisterService attendanceRegisterService;
    @Autowired
    private StaffService staffService;

    public void updateStaffPermissionsForContactDetails(OrgContactUpdateDiff orgContactUpdateDiff) {
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(orgContactUpdateDiff.getRequestInfo()).build();
        String tenantId = orgContactUpdateDiff.getTenantId();
        Set<AttendanceRegister> attendanceRegisterSet = new HashSet<>();
        List<ContactDetails> oldContacts = orgContactUpdateDiff.getOldContacts();

        for(ContactDetails oldContact : oldContacts) {
            AttendanceRegisterSearchCriteria attendanceRegisterSearchCriteria =
                    AttendanceRegisterSearchCriteria.builder().tenantId(tenantId).staffId(oldContact.getIndividualId()).build();
            List<AttendanceRegister> attendanceRegisterList = attendanceRegisterService.searchAttendanceRegister(requestInfoWrapper, attendanceRegisterSearchCriteria);
            revokePermission(attendanceRegisterList, oldContact, orgContactUpdateDiff.getRequestInfo());
            attendanceRegisterSet.addAll(attendanceRegisterList);
        }

        List<ContactDetails> newContacts = orgContactUpdateDiff.getNewContacts();
        grantPermission(attendanceRegisterSet, newContacts, orgContactUpdateDiff.getRequestInfo());
    }

    public void revokePermission(List<AttendanceRegister> attendanceRegisters, ContactDetails oldContact, RequestInfo requestInfo) {
        if(attendanceRegisters.isEmpty()) {
            log.info("No attendance registers to revoke permissions on");
            return;
        }
        List<StaffPermission> staffPermissionList = new ArrayList<>();
        for(AttendanceRegister attendanceRegister : attendanceRegisters) {
            String tenantId = attendanceRegister.getTenantId();
            StaffPermission staffPermission = StaffPermission.builder()
                    .tenantId(tenantId).registerId(attendanceRegister.getId()).userId(oldContact.getIndividualId()).build();
            staffPermissionList.add(staffPermission);
        }
        StaffPermissionRequest staffPermissionRequest = StaffPermissionRequest.builder()
                .requestInfo(requestInfo).staff(staffPermissionList).build();
        staffService.deleteAttendanceStaff(staffPermissionRequest);
        log.info("Revoked permission for: " + oldContact.getIndividualId() + " on " + attendanceRegisters.size() + " registers.");
    }

    public void grantPermission(Set<AttendanceRegister> attendanceRegisters, List<ContactDetails> newContacts, RequestInfo requestInfo) {
        if(attendanceRegisters.isEmpty()) {
            log.info("No attendance registers to grant permission on");
            return;
        }
        List<StaffPermission> staffPermissionList = new ArrayList<>();
        for(AttendanceRegister attendanceRegister : attendanceRegisters) {
            String tenantId = attendanceRegister.getTenantId();
            for(ContactDetails newContact : newContacts) {
                StaffPermission staffPermission = StaffPermission.builder().tenantId(tenantId)
                        .registerId(attendanceRegister.getId()).userId(newContact.getIndividualId()).build();
                staffPermissionList.add(staffPermission);
            }
        }
        StaffPermissionRequest staffPermissionRequest = StaffPermissionRequest.builder()
                .requestInfo(requestInfo).staff(staffPermissionList).build();
        staffService.createAttendanceStaff(staffPermissionRequest);
        log.info("Granted permission on " + attendanceRegisters.size() + " registers for " + newContacts.size() + " new contacts.");
    }

}
