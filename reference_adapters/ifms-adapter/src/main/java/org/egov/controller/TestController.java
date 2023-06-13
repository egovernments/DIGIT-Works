package org.egov.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.egov.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class TestController {

    @Autowired
    private Producer producer;
    @Autowired
    @Qualifier("ifmsRestTemplate")
    private RestTemplate ifmsRestTemplate;

    @RequestMapping(path = "/produce", method = RequestMethod.POST)
    public ResponseEntity<Object> produceRecord(@RequestBody TestObjectProducer testObjectProducer) {
        producer.push(testObjectProducer.getTopic(), testObjectProducer.getJson());
        return new ResponseEntity<>(TextNode.valueOf("Done"), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public ResponseEntity<Object> send(@RequestBody Object object) {
        return ifmsRestTemplate.postForEntity("http://localhost:8080/produce", object, Object.class);
    }

}
