package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElasticsearchQueryConfig {

    @JsonProperty("code")
    private String code;

    @JsonProperty("indexName")
    private String indexName;

    @JsonProperty("query")
    private String query;
}
