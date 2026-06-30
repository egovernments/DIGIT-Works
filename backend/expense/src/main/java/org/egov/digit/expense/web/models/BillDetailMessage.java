package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.digit.expense.web.models.enums.MessageType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailMessage {

    private BillDetail billDetail;

    @Builder.Default
    private MessageType type = MessageType.BILL_DETAIL;

    private String action;
}
