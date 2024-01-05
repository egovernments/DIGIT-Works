package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.digit.exchange.constants.MessageType;
import org.digit.exchange.exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


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
    @NotNull
    private RequestHeader header;
    @NotNull
    @JsonProperty("message")
    @Column(columnDefinition = "TEXT")
    private String message;    

    public RequestMessage(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    public RequestMessage(String to, String from, ExchangeMessage message,MessageType action){
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
        } else if(message instanceof Disbursement){
            Disbursement bill = (Disbursement)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(bill);
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

    static public RequestMessage fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, RequestMessage.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing RequestMessage fromString", e);
		}
	}
}
