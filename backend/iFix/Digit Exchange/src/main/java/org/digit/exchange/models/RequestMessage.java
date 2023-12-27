package org.digit.exchange.models;

import lombok.*;

import org.digit.exchange.constants.Action;
import org.digit.exchange.models.fiscal.Allocation;
import org.digit.exchange.models.fiscal.Demand;
import org.digit.exchange.models.fiscal.Estimate;
import org.digit.exchange.models.fiscal.FiscalMessage;
import org.digit.exchange.models.fiscal.Program;
import org.digit.exchange.models.fiscal.Receipt;
import org.digit.exchange.models.fiscal.Sanction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.UUID;

import jakarta.persistence.*;


@Getter
@Setter
@Entity
@Table(name="request_message")
public class RequestMessage{

    private static final Logger logger = LoggerFactory.getLogger(RequestMessage.class);

    @Id
    private String id;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("header")
    @Embedded
    @OneToOne(cascade = CascadeType.ALL)
    private RequestHeader header;
    @JsonProperty("message")
    @Column(columnDefinition = "TEXT")
    private String message;    

    public RequestMessage(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    public RequestMessage(String to, String from, FiscalMessage message,Action action){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.header = new RequestHeader(to,from,message,action);
        if(message instanceof Program){
            Program program = (Program)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(program);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        } else if(message instanceof Estimate){
            Estimate esimate = (Estimate)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(esimate);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        } else if(message instanceof Sanction){
            Sanction sanction = (Sanction)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(sanction);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        } else if(message instanceof Allocation){
            Allocation allocation = (Allocation)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(allocation);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        } else if(message instanceof Demand){
            Demand demand = (Demand)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(demand);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        } else if(message instanceof Receipt){
            Receipt receipt = (Receipt)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(receipt);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        }
    }
}
