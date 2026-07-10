package org.egov.digit.expense.web.validators;

import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.MdmsUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.egov.digit.expense.config.Constants.ERR_INVALID_PAYMENT_PROVIDER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillValidatorPaymentProviderTest {

    @Mock private MdmsUtil mdmsUtil;
    @Mock private Configuration configs;
    @Mock private BillRepository billRepository;

    @InjectMocks
    private BillValidator validator;

    private Bill billWithProvider(String detailId, String provider) {
        return buildBillWithDetails(Status.PENDING_VERIFICATION,
                List.of(buildDetailWithPayee(detailId, Status.PENDING_VERIFICATION, "0770000001", provider)));
    }

    @Test
    public void unchangedInvalidProvider_echoedFromSearch_passes() {
        Bill request = billWithProvider(DETAIL_ID_1, "");
        Bill search = billWithProvider(DETAIL_ID_1, "");
        assertDoesNotThrow(() -> validator.validatePaymentFieldUpdate(request, search));

        Bill requestOrange = billWithProvider(DETAIL_ID_1, "ORANGE");
        Bill searchOrange = billWithProvider(DETAIL_ID_1, "ORANGE");
        assertDoesNotThrow(() -> validator.validatePaymentFieldUpdate(requestOrange, searchOrange));
    }

    @Test
    public void bulkStatusUpdate_sameDetailInstances_passes() {
        Bill search = billWithProvider(DETAIL_ID_1, "");
        Bill request = buildBillWithDetails(Status.PENDING_VERIFICATION, search.getBillDetails());
        assertDoesNotThrow(() -> validator.validatePaymentFieldUpdate(request, search));
    }

    @Test
    public void blankProvider_introducedByRequest_passes() {
        Bill request = billWithProvider(DETAIL_ID_1, "");
        Bill search = billWithProvider(DETAIL_ID_1, "MTN");
        assertDoesNotThrow(() -> validator.validatePaymentFieldUpdate(request, search));
    }

    @Test
    public void changedToInvalidProvider_throws() {
        Bill request = billWithProvider(DETAIL_ID_1, "ORANGE");
        Bill search = billWithProvider(DETAIL_ID_1, "MTN");
        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validatePaymentFieldUpdate(request, search));
        assertEquals(ERR_INVALID_PAYMENT_PROVIDER, ex.getCode());
    }

    @Test
    public void newDetailWithInvalidProvider_throws() {
        Bill search = billWithProvider(DETAIL_ID_1, "MTN");

        Bill requestNullId = billWithProvider(null, "ORANGE");
        assertThrows(CustomException.class,
                () -> validator.validatePaymentFieldUpdate(requestNullId, search));

        Bill requestUnknownId = billWithProvider(DETAIL_ID_2, "ORANGE");
        assertThrows(CustomException.class,
                () -> validator.validatePaymentFieldUpdate(requestUnknownId, search));

        Bill searchNoPayee = buildBillWithDetails(Status.PENDING_VERIFICATION,
                List.of(buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION)));
        searchNoPayee.getBillDetails().get(0).setPayee(null);
        Bill requestInvalid = billWithProvider(DETAIL_ID_1, "ORANGE");
        assertThrows(CustomException.class,
                () -> validator.validatePaymentFieldUpdate(requestInvalid, searchNoPayee));
    }

    @Test
    public void caseInsensitiveEcho_passes() {
        Bill request = billWithProvider(DETAIL_ID_1, "orange");
        Bill search = billWithProvider(DETAIL_ID_1, "Orange");
        assertDoesNotThrow(() -> validator.validatePaymentFieldUpdate(request, search));
    }

    @Test
    public void validProvider_anyCase_passes() {
        Bill request = billWithProvider(DETAIL_ID_1, "bank");
        Bill search = billWithProvider(DETAIL_ID_1, "MTN");
        assertDoesNotThrow(() -> validator.validatePaymentFieldUpdate(request, search));
    }
}
