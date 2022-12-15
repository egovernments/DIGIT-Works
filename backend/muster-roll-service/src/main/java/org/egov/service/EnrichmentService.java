package org.egov.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.repository.IdGenRepository;
import org.egov.repository.MusterRollRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.MusterRollServiceUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.egov.util.MusterRollServiceConstants.ACTION_REJECT;

@Service
@Slf4j
public class EnrichmentService {

    @Autowired
    private MusterRollServiceUtil musterRollServiceUtil;

    @Autowired
    private MusterRollServiceConfiguration config;

    @Autowired
    private IdGenRepository idGenRepository;

    @Autowired
    private MusterRollRepository musterRollRepository;


    /**
     * Enrich estimate muster roll
     * @param musterRollRequest
     *
     */
    public void enrichEstimateMusterRoll(MusterRollRequest musterRollRequest) {
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();

        //Audit details
        AuditDetails auditDetails = musterRollServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), musterRoll, true);
        musterRoll.setAuditDetails(auditDetails);

        //status
        musterRoll.setStatus(Status.ACTIVE);

    }

    /**
     * Enrich create muster roll
     * @param musterRollRequest
     *
     */
    public void enrichCreateMusterRoll(MusterRollRequest musterRollRequest) {
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();

        //Id
        musterRoll.setId(UUID.randomUUID());

        //Audit details
        AuditDetails auditDetails = musterRollServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), musterRoll, true);
        musterRoll.setAuditDetails(auditDetails);

        //musterRollNumber - Idgen
        String rootTenantId = musterRoll.getTenantId().split("\\.")[0];
        List<String> musterNumbers = getIdList(requestInfo, rootTenantId
                , config.getIdgenMusterRollNumberName(), config.getIdgenMusterRollNumberFormat(), 1);
        if (musterNumbers != null && !musterNumbers.isEmpty()) {
            String musterRollNumber = musterNumbers.get(0);
            musterRoll.setMusterRollNumber(musterRollNumber);
        } else {
            throw new CustomException("MUSTER_NUMBER_NOT_GENERATED","Muster number is not generated from Idgen service");
        }

        //status
        musterRoll.setStatus(Status.ACTIVE);

    }

    /**
     * Enrich update muster roll
     * @param musterRollRequest
     */
    public void enrichUpdateMusterRoll(MusterRollRequest musterRollRequest, MusterRoll existingMusterRoll) {
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        Workflow workflow = musterRollRequest.getWorkflow();


        if (workflow.getAction().equalsIgnoreCase("VERIFY")) {
            //VERIFY action can modify the totalAttendance.
            List<IndividualEntry> individualEntries = existingMusterRoll.getIndividualEntries();
            List<IndividualEntry> modifiedIndividualEntries = musterRoll.getIndividualEntries();
            for (IndividualEntry individualEntry : individualEntries) {
                for (IndividualEntry modifiedIndividualEntry : modifiedIndividualEntries)  {
                    if (modifiedIndividualEntry.getId().toString().equalsIgnoreCase(individualEntry.getId().toString())) {
                        individualEntry.setTotalAttendance(modifiedIndividualEntry.getTotalAttendance());
                        break;
                    }
                }
            }

        }
        musterRoll = existingMusterRoll;
        musterRollRequest.setMusterRoll(musterRoll);

        //AuditDetails
        musterRoll.setAuditDetails(existingMusterRoll.getAuditDetails());
        AuditDetails auditDetails = musterRollServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), musterRoll, false);
        musterRoll.setAuditDetails(auditDetails);

        //populate auditDetails in IndividualEntry and AttendanceEntry
        populateAuditDetailsIndividualEntry(musterRoll);

        if (workflow.getAction().equals(ACTION_REJECT)) {
            enrichUpdateMusterWorkFlowForActionReject(musterRollRequest);
        }

    }

    /**
     * If the workflow action is 'REJECT' then assignee will be updated
     * with user id that is having role as 'ORG_STAFF' or 'ORG_ADMIN' (i.e the 'auditDetails.createdBy')
     *
     * @param request
     */
    private void enrichUpdateMusterWorkFlowForActionReject(MusterRollRequest request) {
        Workflow workflow = request.getWorkflow();
        AuditDetails auditDetails = request.getMusterRoll().getAuditDetails();
        if (auditDetails != null && StringUtils.isNotBlank(auditDetails.getCreatedBy())) {
            List<String> updatedAssignees = new ArrayList<>();
            updatedAssignees.add(auditDetails.getCreatedBy());
            workflow.setAssignees(updatedAssignees);
        }
    }

    /**
     * Populate audit details for individualEntry
     *
     * @param musterRoll
     */
    private void populateAuditDetailsIndividualEntry(MusterRoll musterRoll) {
       for (IndividualEntry individualEntry : musterRoll.getIndividualEntries()) {
           individualEntry.setAuditDetails(musterRoll.getAuditDetails());
           populateAuditDetailsAttendanceEntry(individualEntry);
       }
    }

    /**
     * Populate audit details for attendanceEntry
     *
     * @param individualEntry
     */
    private void populateAuditDetailsAttendanceEntry(IndividualEntry individualEntry) {
        for (AttendanceEntry attendanceEntry : individualEntry.getAttendanceEntries()) {
            attendanceEntry.setAuditDetails(individualEntry.getAuditDetails());
        }
    }

    /**
     * Returns a list of numbers generated from idgen
     *
     * @param requestInfo RequestInfo from the request
     * @param tenantId    tenantId of the city
     * @param idKey       code of the field defined in application properties for which ids are generated for
     * @param idformat    format in which ids are to be generated
     * @param count       Number of ids to be generated
     * @return List of ids generated using idGen service
     */
    private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey,
                                   String idformat, int count) {
        List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count).getIdResponses();

        if (CollectionUtils.isEmpty(idResponses))
            throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");

        return idResponses.stream()
                .map(IdResponse::getId).collect(Collectors.toList());
    }
}
