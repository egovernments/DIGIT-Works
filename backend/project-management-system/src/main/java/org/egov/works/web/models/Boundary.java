package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Boundary
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Boundary {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("parentid")
    private String parentid = null;

    @JsonProperty("addressid")
    private String addressid = null;

    @JsonProperty("code")
    private String code = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("label")
    private String label = null;

    @JsonProperty("latitude")
    private String latitude = null;

    @JsonProperty("longitude")
    private String longitude = null;

    @JsonProperty("children")
    @Valid
    private List<Boundary> children = null;

    @JsonProperty("materializedPath")
    private String materializedPath = null;


    public Boundary addChildrenItem(Boundary childrenItem) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(childrenItem);
        return this;
    }

}

