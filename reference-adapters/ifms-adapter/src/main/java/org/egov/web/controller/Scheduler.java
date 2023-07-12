package org.egov.web.controller;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.PDService;
import org.egov.service.FailureDetailsService;
import org.egov.service.PAGService;
import org.egov.service.PISService;
import org.egov.service.VirtualAllotmentService;
import org.egov.utils.ResponseInfoFactory;
import org.egov.web.models.enums.JITServiceId;
import org.egov.web.models.jit.SchedulerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;


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

        }

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(schedulerRequest.getRequestInfo(), true);
        return responseInfo;
    }

}
