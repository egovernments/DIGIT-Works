package org.egov.web.models;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TestObjectProducer {

    private String topic;

    private JsonNode json;

}
