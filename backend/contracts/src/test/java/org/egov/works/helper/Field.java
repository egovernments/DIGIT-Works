package org.egov.works.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Field
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Field {
    @JsonProperty("key")
    private String key = null;

    @JsonProperty("value")
    private String value = null;


}
