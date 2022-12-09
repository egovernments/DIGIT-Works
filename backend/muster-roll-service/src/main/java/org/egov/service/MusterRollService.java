package org.egov.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.kafka.Producer;
import org.egov.validator.MusterRollValidator;
import org.egov.web.models.AttendanceTime;
import org.egov.web.models.MusterRoll;
import org.egov.web.models.MusterRollRequest;
import org.egov.web.models.MusterRollSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MusterRollService {

    @Autowired
    private MusterRollValidator musterRollValidator;

    @Autowired
    private EnrichmentService enrichmentService;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private Producer producer;

    @Autowired
    private MusterRollServiceConfiguration serviceConfiguration;


    /**
     * Provide the estimate of the muster roll
     *
     * @param musterRollRequest
     * @return
     */
    public MusterRollRequest estimateMusterRoll(MusterRollRequest musterRollRequest) {
        AttendanceTime attendanceTime = musterRollValidator.validateEstimateMusterRoll(musterRollRequest);
        enrichmentService.enrichEstimateMusterRoll(musterRollRequest, attendanceTime);
        return musterRollRequest;
    }


    /**
     * Create a muster roll record
     *
     * @param musterRollRequest
     * @return
     */
    public MusterRollRequest createMusterRoll(MusterRollRequest musterRollRequest) {
        AttendanceTime attendanceTime =  musterRollValidator.validateCreateMusterRoll(musterRollRequest);
        enrichmentService.enrichCreateMusterRoll(musterRollRequest,attendanceTime);
        workflowService.updateWorkflowStatus(musterRollRequest);
        //producer.push(serviceConfiguration.getSaveMusterRollTopic(), musterRollRequest);
        return musterRollRequest;
    }

    /**
     * Search muster roll based on given search criteria
     *
     * @param requestInfoWrapper
     * @param searchCriteria
     * @return
     */
    public List<MusterRoll> searchMusterRolls(RequestInfoWrapper requestInfoWrapper, MusterRollSearchCriteria searchCriteria) {
        return null;
    }

    /**
     * Update the given muster roll details
     *
     * @param musterRollRequest
     * @return
     */
    public MusterRollRequest updateMusterRoll(MusterRollRequest musterRollRequest) {
        return musterRollRequest;
    }
}
