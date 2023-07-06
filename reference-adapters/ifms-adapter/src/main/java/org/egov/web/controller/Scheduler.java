package org.egov.web.controller;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
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

    @RequestMapping(path = "_scheduler", method = RequestMethod.POST)
    public ResponseInfo scheduler(@RequestBody @Valid SchedulerRequest schedulerRequest, @RequestParam("serviceId") JITServiceId serviceId )throws Exception{

        switch (serviceId){
            case PAG:

                break;
            case PIS:
                pisService.updatePIStatus();
                break;
            case VA:
                virtualAllotmentService.generateVirtualAllotment(schedulerRequest.getRequestInfo());
                break;

        }

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(schedulerRequest.getRequestInfo(), true);
        return responseInfo;
    }

}
