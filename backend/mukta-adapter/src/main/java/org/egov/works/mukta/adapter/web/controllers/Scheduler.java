package org.egov.works.mukta.adapter.web.controllers;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.mukta.adapter.service.PaymentService;
import org.egov.works.mukta.adapter.util.ResponseInfoFactory;
import org.egov.works.mukta.adapter.web.models.enums.JITServiceId;
import org.egov.works.mukta.adapter.web.models.jit.SchedulerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class Scheduler {

    @Autowired
    private ResponseInfoFactory responseInfoFactory;
    @Autowired
    private PaymentService paymentService;

    /**
     * The function receives request from cronjob and routes it to the appropriate service based on the serviceId in url.
     *
     * @param schedulerRequest
     * @param serviceId
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "_scheduler", method = RequestMethod.POST)
    public ResponseInfo scheduler(@RequestBody @Valid SchedulerRequest schedulerRequest,
                                  @RequestParam("serviceId") JITServiceId serviceId) throws Exception {

        // Switch case is used to direct request to the respective services.
        switch (serviceId) {
            case PA:
                paymentService.createPaymentFromBills(schedulerRequest.getRequestInfo());
                break;
        }

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(schedulerRequest.getRequestInfo(), true);
        return responseInfo;
    }

}
