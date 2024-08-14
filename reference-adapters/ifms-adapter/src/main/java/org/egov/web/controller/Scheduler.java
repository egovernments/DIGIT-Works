package org.egov.web.controller;

import javax.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.*;
import org.egov.utils.ResponseInfoFactory;
import org.egov.web.models.enums.JITServiceId;
import org.egov.web.models.jit.SchedulerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Scheduler {

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private PISService pisService;
    @Autowired
    VirtualAllotmentService virtualAllotmentService;
    @Autowired
    private FailureDetailsService failureDetailsService;
    @Autowired
    private PDService pdService;
    @Autowired
    private PAGService pagService;
    @Autowired
    private PaymentService paymentService;

    /**
     * The function receives request from cronjob and routes it to the appropriate service based on the serviceId in url.
     * @param schedulerRequest
     * @param serviceId
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "_scheduler", method = RequestMethod.POST)
    public ResponseInfo scheduler(@RequestBody @Valid SchedulerRequest schedulerRequest,
                                  @RequestParam("serviceId") JITServiceId serviceId )throws Exception{

        // Switch case is used to direct request to the respective services.
        switch (serviceId){
            case PAG:
                pagService.updatePAG(schedulerRequest.getRequestInfo());
                break;
            case PIS:
                pisService.updatePIStatus(schedulerRequest.getRequestInfo());
                break;
            case VA:
                virtualAllotmentService.generateVirtualAllotment(schedulerRequest.getRequestInfo());
                break;
            case PD:
                pdService.updatePDStatus(schedulerRequest.getRequestInfo());
                break;
            case FD:
                // Call mock FD service data sync API first
                failureDetailsService.updateMockFailureDetails(schedulerRequest.getRequestInfo());
                failureDetailsService.updateFailureDetails(schedulerRequest.getRequestInfo());
                break;
            case FTPS:
                failureDetailsService.updateFTPSStatus(schedulerRequest.getRequestInfo());
                break;
            case FTFPS:
                failureDetailsService.updateFTFPSStatus(schedulerRequest.getRequestInfo());
                break;
            case PA:
                paymentService.createPaymentFromBills(schedulerRequest.getRequestInfo());
                break;
        }

        return responseInfoFactory.createResponseInfoFromRequestInfo(schedulerRequest.getRequestInfo(), true);
    }

}
