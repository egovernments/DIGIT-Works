package org.egov.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.egov.web.models.Field;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * AdditionalFields
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalFields   {
        @JsonProperty("schema")
        private String schema = null;

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

