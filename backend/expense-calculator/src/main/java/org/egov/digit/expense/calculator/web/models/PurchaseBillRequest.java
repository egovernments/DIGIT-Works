package org.egov.digit.expense.calculator.web.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PurchaseBillRequest
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-17T16:59:23.221+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseBillRequest   {
        @JsonProperty("requestInfo")
        @Valid
        private RequestInfo requestInfo = null;

        @JsonProperty("bill")
        @Valid
        private Bill bill = null;

        @JsonProperty("documents")
        @Valid
        private List<Document> documents = null;

        public PurchaseBillRequest addDocumentsItem(Document documentsItem) {
            if (this.documents == null) {
            this.documents = new ArrayList<>();
            }
        this.documents.add(documentsItem);
        return this;
        }
}
