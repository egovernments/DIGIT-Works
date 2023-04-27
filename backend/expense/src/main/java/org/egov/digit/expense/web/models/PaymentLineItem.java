package org.egov.digit.expense.web.models;

import java.math.BigDecimal;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Line items are the amount breakups for net amounts
 */
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentLineItem   {
	
  @JsonProperty("id")
  private String id;

  @JsonProperty("lineItemid")
  private String lineItemid;

  @JsonProperty("tenantId")
  private String tenantId;

  @JsonProperty("headCode")
  private String headCode;

  @JsonProperty("amount")
  private BigDecimal amount;

  /**
   * Type of line item
   */
  public enum TypeEnum {
	  
    PAYABLE("PAYABLE"),
    
    DEDUCTION("DEDUCTION");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  
  @JsonProperty("type")
  private TypeEnum type;

  @JsonProperty("paidAmount")
  private BigDecimal paidAmount;

  @JsonProperty("auditDetails")
  private AuditDetails auditDetails;
}
