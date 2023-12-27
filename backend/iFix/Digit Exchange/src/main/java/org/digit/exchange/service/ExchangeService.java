package org.digit.exchange.service;

import java.util.List;
import java.util.Optional;

import org.digit.exchange.models.RequestMessage;
import org.digit.exchange.repository.RequestMessageRepository;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

    private final RequestMessageRepository requestMessageRepository;

    public ExchangeService(RequestMessageRepository requestMessageRepository){
        this.requestMessageRepository = requestMessageRepository;
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
    
}
