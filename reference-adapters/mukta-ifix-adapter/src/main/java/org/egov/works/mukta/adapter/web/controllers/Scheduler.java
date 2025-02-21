package org.egov.works.mukta.adapter.web.controllers;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.mukta.adapter.kafka.MuktaAdaptorProducer;
import org.egov.works.mukta.adapter.service.DisbursementService;
import org.egov.works.mukta.adapter.service.PaymentInstructionService;
import org.egov.works.mukta.adapter.service.PaymentService;
import org.egov.works.mukta.adapter.util.ResponseInfoFactory;
import org.egov.works.mukta.adapter.web.models.PaymentRequest;
import org.egov.works.mukta.adapter.web.models.enums.JITServiceId;
import org.egov.works.mukta.adapter.web.models.jit.SchedulerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Objects;


@RestController
public class Scheduler {

    private final ResponseInfoFactory responseInfoFactory;
    private final PaymentService paymentService;
    private final MuktaAdaptorProducer muktaAdaptorProducer;

    @Autowired
    public Scheduler(ResponseInfoFactory responseInfoFactory, PaymentService paymentService, DisbursementService disbursementService, MuktaAdaptorProducer muktaAdaptorProducer, PaymentInstructionService paymentInstructionService) {
        this.responseInfoFactory = responseInfoFactory;
        this.paymentService = paymentService;
        this.muktaAdaptorProducer = muktaAdaptorProducer;
    }

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
                                  @RequestParam("serviceId") JITServiceId serviceId) {
        // Switch case is used to direct request to the respective services.
        if (Objects.requireNonNull(serviceId) == JITServiceId.PA) {
            paymentService.createPaymentFromBills(schedulerRequest.getRequestInfo());
        }

        return responseInfoFactory.createResponseInfoFromRequestInfo(schedulerRequest.getRequestInfo(), true);
    }

    @RequestMapping(path = "/_test", method = RequestMethod.POST)
    public void test(@RequestBody @Valid PaymentRequest paymentRequest
    ) {
        muktaAdaptorProducer.push("expense-payment-create", paymentRequest);
    }
}
