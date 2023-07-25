package org.egov.web.controller;

import javax.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.FailureDetailsService;
import org.egov.service.PAGService;
import org.egov.service.PDService;
import org.egov.service.PISService;
import org.egov.service.VirtualAllotmentService;
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

    @RequestMapping(path = "_scheduler", method = RequestMethod.POST)
    public ResponseInfo scheduler(@RequestBody @Valid SchedulerRequest schedulerRequest, @RequestParam("serviceId") JITServiceId serviceId )throws Exception{

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
                failureDetailsService.updateFailureDetails(schedulerRequest.getRequestInfo());
                break;
            case FTPS:
                failureDetailsService.updateFTPSStatus(schedulerRequest.getRequestInfo());
                break;
            case FTFPS:
                failureDetailsService.updateFTFPSStatus(schedulerRequest.getRequestInfo());
                break;

        }

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(schedulerRequest.getRequestInfo(), true);
        return responseInfo;
    }

}
