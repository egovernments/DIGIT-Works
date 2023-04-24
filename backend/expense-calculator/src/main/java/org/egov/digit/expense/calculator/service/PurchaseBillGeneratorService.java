package org.egov.digit.expense.calculator.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants;
import org.egov.digit.expense.calculator.util.IdgenUtil;
import org.egov.digit.expense.calculator.util.MdmsUtils;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillDetail;
import org.egov.digit.expense.calculator.web.models.Payer;
import org.egov.digit.expense.calculator.web.models.PurchaseBill;
import org.egov.digit.expense.calculator.web.models.PurchaseBillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PurchaseBillGeneratorService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExpenseCalculatorConfiguration configs;
    
    @Autowired
    private IdgenUtil idgenUtil;
    
    @Autowired
    private MdmsUtils mdmsUtil;

    public Bill createPurchaseBill(PurchaseBillRequest purchaseBillRequest){
        PurchaseBill bill = purchaseBillRequest.getBill();
        Bill purchaseBill = new Bill();
        if(isValidPurchaseBill(bill)) {
	        //Generate referenceId
	        String referenceId = generateReferenceId(purchaseBillRequest, bill.getContractNumber());
	        //Get Payers from MDMS. Filter based on some criteria
	        Map<String,String> payerList = fetchMDMSDataForPayers(purchaseBillRequest.getRequestInfo(), bill.getTenantId());
	        //Validate Payee depending on payee type. If payee is org, validate orgId. If payee is individual, validate individualId
	        //Validate line item head codes against MDMS
	        //Generate payableLineItems
	        //Create bill
	        
	//        	purchaseBill = Bill.builder()
	//                    .tenantId(bill.getTenantId())
	//                    .billDate(bill.getInvoiceDate())
	//                    .netPayableAmount(bill.getNetPayableAmount())
	//                    .businessService("works.purchase")
	//                    .referenceId(bill.getReferenceId())
	//                    .status(bill.getStatus())
	//                    .payer(bill.getPayer())
	//                    .billDetails(bill.getBillDetails())
	//                    .additionalDetails(bill.getAdditionalDetails())
	//                    .build();
        }
        return purchaseBill;
    }
    
    /**
     * Validate purchase bill parameters and make sure everything is correct.
     * Primarily, we need to have the tenantId and billDetails object
     * @param bill
     * @return
     */
    private boolean isValidPurchaseBill(PurchaseBill bill) {
    	boolean isValid = true;
    	if(bill.getTenantId()==null || bill.getTenantId().length()==0) {
    		isValid = false;
    	}
    	if(bill.getBillDetails() == null || bill.getBillDetails().size()==0)  {
    		isValid=false;
    	}
    	else {
    		//Make sure payee is set for each bill detail object
    		for(BillDetail detail: bill.getBillDetails()) {
    			//If payee is not set, break out immediately
    			if(detail.getPayee() == null) {
    				isValid = false;
    				break;
    			}
    			//If payee is set, make sure type and identifier are set.
    			else {
    				if(detail.getPayee().getType() == null || detail.getPayee().getIdentifier()==null) {
    					isValid=false;
    					break;
    				}
    			}
    		}
    	}
    	return isValid;
    }
    
    private Map<String,String> fetchMDMSDataForPayers(RequestInfo requestInfo, String tenantId){
        String rootTenantId = tenantId.split("\\.")[0];
        log.info("Fetch payer list from MDMS");
        Object mdmsData = mdmsUtil.fetchMDMSDataForPayerList(requestInfo, rootTenantId);
        List<Object> payerListJson = commonUtil.readJSONPathValue(mdmsData, ExpenseCalculatorServiceConstants.JSON_PATH_FOR_PAYER_LIST);
        Map<String,String> payerListCodeMapping = new HashMap<>();
        for(Object obj : payerListJson){
            Payer payer = mapper.convertValue(obj, Payer.class);
            payerListCodeMapping.put(payer.getCode(),payer.getType());
        }
        log.info("Payer fetched from MDMS");
        return payerListCodeMapping;
    }
    
    /**
     * Generates the referenceId to be set on the Bill object. This referenceId is a combination of the
     * contract number and a unique ID generated from MDMS format template. 
     * Example: CON123_PB_123
     * @param request
     * @param contractNumber
     * @return
     */
    private String generateReferenceId(PurchaseBillRequest request, String contractNumber) {
    	 RequestInfo requestInfo = request.getRequestInfo();
         PurchaseBill bill = request.getBill();
         String rootTenantId = bill.getTenantId().split("\\.")[0];
         List<String> idList = idgenUtil.getIdList(requestInfo, rootTenantId, configs.getPurchaseBillReferenceIdFormatKey(), "", 1);
         if (idList==null || idList.size()==0) {
        	 //Throw exception here
        	 return null;
         }
         String generatedUniqueId = idList.get(0);
         String referenceId = contractNumber + "_" + generatedUniqueId;
         log.info("ReferenceId generated. Generated reference ID is ["+ referenceId + "]");
         return referenceId;
    }
}
