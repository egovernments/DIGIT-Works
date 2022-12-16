package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductRequest
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;

    @JsonProperty("Product")
    @Valid
    private List<Product> product = new ArrayList<>();

    @JsonProperty("apiOperation")
    private ApiOperation apiOperation = null;


    public ProductRequest addProductItem(Product productItem) {
        this.product.add(productItem);
        return this;
    }

}

