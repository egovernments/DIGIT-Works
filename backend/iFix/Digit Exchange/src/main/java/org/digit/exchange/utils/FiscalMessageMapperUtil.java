package org.digit.exchange.utils;

import org.digit.exchange.web.models.fiscal.Allocation;
import org.digit.exchange.web.models.fiscal.Demand;
import org.digit.exchange.web.models.fiscal.Estimate;
import org.digit.exchange.web.models.fiscal.FiscalMessage;
import org.digit.exchange.web.models.fiscal.Program;
import org.digit.exchange.web.models.fiscal.Receipt;
import org.digit.exchange.web.models.fiscal.Sanction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.JsonProcessingException;


@Service
public class FiscalMessageMapperUtil {

    public FiscalMessageMapperUtil() {
    }

    public FiscalMessage formatMessage(String fiscalMessageType, String fiscalMessage) {
        
        if(fiscalMessageType.equalsIgnoreCase("program")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                return mapper.readValue(fiscalMessage, Program.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(fiscalMessageType.equals("estimate")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                return mapper.readValue(fiscalMessage, Estimate.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(fiscalMessageType.equals("sanction")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                return mapper.readValue(fiscalMessage, Sanction.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(fiscalMessageType.equals("allocation")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                return mapper.readValue(fiscalMessage, Allocation.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(fiscalMessageType.equals("demand")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                return mapper.readValue(fiscalMessage, Demand.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(fiscalMessageType.equals("receipt")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                return mapper.readValue(fiscalMessage, Receipt.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
