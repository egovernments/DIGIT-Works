package org.egov.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.kafka.Producer;
import org.egov.repository.MusterRollRepository;
import org.egov.tracer.model.CustomException;
import org.egov.validator.MusterRollValidator;
import org.egov.web.models.MusterRoll;
import org.egov.web.models.MusterRollRequest;
import org.egov.web.models.MusterRollSearchCriteria;
import org.egov.web.models.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MusterRollService {

    @Autowired
    private MusterRollValidator musterRollValidator;

    @Autowired
    private EnrichmentService enrichmentService;

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private Producer producer;

    @Autowired
    private MusterRollServiceConfiguration serviceConfiguration;

    @Autowired
    private MusterRollRepository musterRollRepository;


    /**
     * Provide the estimate of the muster roll
     *
     * @param musterRollRequest
     * @return
     */
    public MusterRollRequest estimateMusterRoll(MusterRollRequest musterRollRequest) {
        musterRollValidator.validateEstimateMusterRoll(musterRollRequest);
        enrichmentService.enrichEstimateMusterRoll(musterRollRequest);
        calculationService.createAttendance(musterRollRequest,false);
        return musterRollRequest;
    }


    /**
     * Create a muster roll record
     *
     * @param musterRollRequest
     * @return
     */
    public MusterRollRequest createMusterRoll(MusterRollRequest musterRollRequest) {
        checkMusterRollExists(musterRollRequest.getMusterRoll());
        musterRollValidator.validateCreateMusterRoll(musterRollRequest);
        enrichmentService.enrichCreateMusterRoll(musterRollRequest);
        calculationService.createAttendance(musterRollRequest,true);
        workflowService.updateWorkflowStatus(musterRollRequest);
        producer.push(serviceConfiguration.getSaveMusterRollTopic(), musterRollRequest);
        return musterRollRequest;
    }

    /**
     * Search muster roll based on the given search criteria
     *
     * @param requestInfoWrapper
     * @param searchCriteria
     * @return
     */
    public List<MusterRoll> searchMusterRolls(RequestInfoWrapper requestInfoWrapper, MusterRollSearchCriteria searchCriteria) {
        musterRollValidator.validateSearchMuster(requestInfoWrapper,searchCriteria);
        List<MusterRoll> musterRollList = musterRollRepository.getMusterRoll(searchCriteria);
        return musterRollList;
    }

    /**
     * Update the muster roll
     *
     * @param musterRollRequest
     * @return
     */
    public MusterRollRequest updateMusterRoll(MusterRollRequest musterRollRequest) {
        MusterRoll existingMusterRoll = fetchExistingMusterRoll(musterRollRequest.getMusterRoll());
        musterRollValidator.validateUpdateMusterRoll(musterRollRequest);
        enrichmentService.enrichUpdateMusterRoll(musterRollRequest,existingMusterRoll);
        Workflow workflow = musterRollRequest.getWorkflow();
        //If the ACTION is 'RESUBMIT', re-calculate the attendance from attendanceLogs and update
        if (workflow.getAction().equalsIgnoreCase("RESUBMIT")) {
            calculationService.updateAttendance(musterRollRequest);
        }
        workflowService.updateWorkflowStatus(musterRollRequest);
        producer.push(serviceConfiguration.getUpdateMusterRollTopic(), musterRollRequest);
        return musterRollRequest;
    }

    /**
     * Check if the muster roll already exists for the same registerId, startDate and endDate to avoid duplicate muster creation
     * @param musterRoll
     */
    public void checkMusterRollExists(MusterRoll musterRoll) {
        MusterRollSearchCriteria searchCriteria = MusterRollSearchCriteria.builder().tenantId(musterRoll.getTenantId())
                .registerId(musterRoll.getRegisterId()).fromDate(musterRoll.getStartDate())
                .toDate(musterRoll.getEndDate().doubleValue()).build();
        List<MusterRoll> musterRolls = musterRollRepository.getMusterRoll(searchCriteria);
        if (!CollectionUtils.isEmpty(musterRolls)) {
            throw new CustomException("DUPLICATE_MUSTER_ROLL","Muster roll already exists for this register and date");
        }
    }

    /**
     * Fetch the existing muster roll from DB else throw error
     * @param musterRoll
     * @return
     */
    public MusterRoll fetchExistingMusterRoll(MusterRoll musterRoll) {
        List<String> ids = new ArrayList<>();
        ids.add(musterRoll.getId().toString());
        MusterRollSearchCriteria searchCriteria = MusterRollSearchCriteria.builder().ids(ids).tenantId(musterRoll.getTenantId()).build();
        List<MusterRoll> musterRolls = musterRollRepository.getMusterRoll(searchCriteria);
        if (CollectionUtils.isEmpty(musterRolls)) {
            throw new CustomException("NO_MATCH_FOUND","Invalid Muster roll id");
        }
        MusterRoll existingMusterRoll = musterRolls.get(0);
        return existingMusterRoll;
    }
}
