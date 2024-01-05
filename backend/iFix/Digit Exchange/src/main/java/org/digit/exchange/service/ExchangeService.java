package org.digit.exchange.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.digit.exchange.config.AppConfig;
import org.digit.exchange.kafka.ExchangeProducer;
import org.digit.exchange.utils.DispatcherUtil;
import org.digit.exchange.web.models.*;
import org.digit.exchange.repository.RequestMessageRepository;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

    private final RequestMessageRepository requestMessageRepository;
    private final ExchangeProducer producer;
    private final AppConfig config;
    private final DispatcherUtil dispatcher;
    private final ObjectMapper mapper;

    public ExchangeService(RequestMessageRepository requestMessageRepository, ExchangeProducer producer, AppConfig config, DispatcherUtil dispatcher, ObjectMapper mapper) {
        this.requestMessageRepository = requestMessageRepository;
        this.producer = producer;
        this.config = config;
        this.dispatcher = dispatcher;
        this.mapper = mapper;
    }


    // Create
    public RequestMessage createRequestMessage(RequestMessage requestMessage) {
        return requestMessageRepository.save(requestMessage);
    }

    // Read
    public Optional<RequestMessage> getRequestMessageById(String id) {
        return requestMessageRepository.findById(id);
    }

    public List<RequestMessage> getAllRequestMessages() {
        return requestMessageRepository.findAll();
    }

    // Update
    public RequestMessage updateRequestMessage(RequestMessage requestMessage) {
        // Assuming the requestMessage has a valid ID
        return requestMessageRepository.save(requestMessage);
    }

    // Delete
    public void deleteRequestMessage(String id) {
        requestMessageRepository.deleteById(id);
    }

    public RequestMessage processMessage(String subpath, RequestMessage messageRequest) {
        RequestMessageWrapper requestMessageWrapper = new RequestMessageWrapper(subpath, messageRequest);
        producer.push( "exchange-topic", requestMessageWrapper);
        return messageRequest;
    }

    public void send(RequestMessageWrapper requestMessageWrapper) {
        if (requestMessageWrapper.getType().contains("on-")){
            send(requestMessageWrapper.getRequestMessage(), true);
        } else {
            send(requestMessageWrapper.getRequestMessage(), false);
        }

    }
    public RequestMessage send(RequestMessage requestMessage, Boolean isReply) {

        String url = getReceiverEndPoint(requestMessage,isReply);
        String result = null;
        if(url==null)
            return null;
        switch (requestMessage.getHeader().getMessageType()) {
            case INDIVIDUAL:
                ExchangeMessage individual = ExchangeMessage.fromString(requestMessage.getMessage());
                //Verify the content
                //Sign the message
                //Send the message
                result = dispatcher.dispatch(url, individual);
                break;

            case ORGANISATION:
                Organisation organisation = Organisation.fromString(requestMessage.getMessage());
                result = dispatcher.dispatch(url, organisation);
                break;

            case PROGRAM:
                Program program = Program.fromString(requestMessage.getMessage());
                result = dispatcher.dispatch(url, program);
                break;

            case ESTIMATE:
                Estimate estimate = Estimate.fromString(requestMessage.getMessage());
                result = dispatcher.dispatch(url, estimate);
                break;

            case SANCTION:
                Sanction sanction = Sanction.fromString(requestMessage.getMessage());
                result = dispatcher.dispatch(url, sanction);
                break;

            case ALLOCATION:
                Allocation allocation = Allocation.fromString(requestMessage.getMessage());
                result = dispatcher.dispatch(url, allocation);
                break;

            case DISBURSEMENT:
                Disbursement bill = Disbursement.fromString(requestMessage.getMessage());
                dispatcher.dispatch(url, bill);
                break;

            case PAYMENT:
                Payment payment = Payment.fromString(requestMessage.getMessage());
                result = dispatcher.dispatch(url, payment);
                break;

            case DEMAND:
                Demand demand = Demand.fromString(requestMessage.getMessage());
                result = dispatcher.dispatch(url, demand);
                break;

            case RECEIPT:
                Receipt receipt = Receipt.fromString(requestMessage.getMessage());
                result = dispatcher.dispatch(url, receipt);
                break;
            default:
                break;
        }
//        if (result == null || result){
//
//        }
        return requestMessageRepository.save(requestMessage);
    }

    String getReceiverEndPoint(RequestMessage message, Boolean isReply){
        String recieverDomain = parseDID(message.getHeader().getReceiverId())[1];
        if( recieverDomain.equalsIgnoreCase(config.getDomain())){
            //Retrieve url from config
            if(isReply)
                return config.getRoutes().get("ON-" + message.getHeader().getMessageType().toString());
            else
                return config.getRoutes().get(message.getHeader().getMessageType().toString());
        }else{
            return recieverDomain + "/exchange/v1/" + message.getHeader().getMessageType().toString().toLowerCase();
        }
    }

    public String[] parseDID(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        String[] parts = input.split("@", 2); // Limit to 2 parts in case there are multiple '@' in the string
        return parts; // The first part before the '@'
    }
}
