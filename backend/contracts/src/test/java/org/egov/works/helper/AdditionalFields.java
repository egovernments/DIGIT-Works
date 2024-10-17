package org.egov.works.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * AdditionalFields
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalFields {
    @JsonProperty("schema")
    private String schema = null;
    @JsonProperty("ward")
    private String ward = null;
    @JsonProperty("projectId")
    private String projectId = null;
    @JsonProperty("projectName")
    private String projectName = null;
    @JsonProperty("version")
    private Integer version = null;

    @JsonProperty("fields")
    @Valid
    private List<Field> fields = null;


    public AdditionalFields addFieldsItem(Field fieldsItem) {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        this.fields.add(fieldsItem);
        return this;
    }

}
