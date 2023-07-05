package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.producer.Producer;
import org.egov.config.IfmsAdapterConfig;
import org.egov.utils.BillUtils;
import org.egov.web.models.bill.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    BillUtils billUtils;

    @Autowired
    Producer producer;

    @Autowired
    IfmsAdapterConfig serviceConfiguration;

    public void updatePayments(PaymentRequest paymentRequest) {

        Payment payment = paymentRequest.getPayment();
        // Get the list of bills based on payment request
        List<Bill> billList =  billUtils.fetchBillsFromPayment(paymentRequest);
        ObjectMapper mapper = new ObjectMapper();
         if(billList != null && !billList.isEmpty()) {
             for (Bill bill : billList) {
                 String billNumber = bill.getBillNumber();
                 for(BillDetail billDetail : bill.getBillDetails()) {
                     String referenceId = billDetail.getReferenceId();

                     JsonNode paymentBillNode = mapper.convertValue(bill, JsonNode.class);
                     ObjectNode additionalDetailsNode = mapper.createObjectNode();

                     additionalDetailsNode.put("billNumber", billNumber);
                     additionalDetailsNode.put("referenceId", referenceId);

                     if(payment.getAdditionalDetails() != null)
                     {
                         ((ObjectNode) paymentBillNode).set("additionalDetails", (JsonNode) payment.getAdditionalDetails());
                     }
                     ((ObjectNode) paymentBillNode).set("additionalDetails", additionalDetailsNode);
                     PaymentBill modifiedPaymentBill = mapper.convertValue(paymentBillNode, PaymentBill.class);
                     payment.addBillItem(modifiedPaymentBill);
                 }
             }
         }
         producer.push(serviceConfiguration.getPaymentEnrichTopic(), paymentRequest);
    }
}
