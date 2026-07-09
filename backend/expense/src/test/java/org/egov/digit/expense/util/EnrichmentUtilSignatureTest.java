package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.request.User;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.BillSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EnrichmentUtilSignatureTest {

    @Mock private Configuration config;
    @Mock private IdgenUtil idgenUtil;
    @Mock private GenderUtil genderUtil;
    @Mock private WorkerRegistryUtil workerRegistryUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private EnrichmentUtil enrichmentUtil;

    @BeforeEach
    void setUp() {
        enrichmentUtil = new EnrichmentUtil(config, idgenUtil, genderUtil, objectMapper, workerRegistryUtil);
    }

    private BillRequest request(Bill bill, String action, String... roleCodes) {
        List<Role> roles = new ArrayList<>();
        for (String code : roleCodes) {
            roles.add(Role.builder().code(code).build());
        }
        User user = User.builder().uuid("user-uuid-1").roles(roles).build();
        RequestInfo requestInfo = RequestInfo.builder().userInfo(user).build();
        Workflow workflow = action == null ? null : Workflow.builder().action(action).build();
        return BillRequest.builder().bill(bill).requestInfo(requestInfo).workflow(workflow).build();
    }

    @Test
    void stampsIdentityOnNewSignatureAndSyncsAdditionalDetails() {
        BillSignature incoming = BillSignature.builder().printedName("  Jane Doe  ").fileStoreId("fs-1").build();
        Bill bill = Bill.builder().signatures(new ArrayList<>(Collections.singletonList(incoming))).build();
        BillRequest billRequest = request(bill, "SEND_FOR_APPROVAL", "PAYMENT_REVIEWER");

        enrichmentUtil.enrichBillSignatures(billRequest, null);

        assertEquals(1, bill.getSignatures().size());
        BillSignature enriched = bill.getSignatures().get(0);
        assertNotNull(enriched.getId());
        assertNotNull(enriched.getSignedTime());
        assertEquals("user-uuid-1", enriched.getSignedBy());
        assertEquals("Jane Doe", enriched.getPrintedName());
        assertEquals("SEND_FOR_APPROVAL", enriched.getAction());
        assertEquals("PAYMENT_REVIEWER", enriched.getRole());

        JsonNode details = objectMapper.valueToTree(bill.getAdditionalDetails());
        assertTrue(details.get("signatures").isArray());
        assertEquals("Jane Doe", details.get("signatures").get(0).get("printedName").asText());
    }

    @Test
    void preservesExistingSignaturesWhenMerging() {
        BillSignature existing = BillSignature.builder()
                .id("sig-old").printedName("Old Editor").fileStoreId("fs-old")
                .action("SEND_FOR_REVIEW").role("PAYMENT_EDITOR").signedTime(1L).build();
        Bill billFromSearch = Bill.builder().signatures(Collections.singletonList(existing)).build();

        BillSignature incoming = BillSignature.builder().printedName("New Approver").fileStoreId("fs-new").build();
        Bill bill = Bill.builder().signatures(new ArrayList<>(Collections.singletonList(incoming))).build();
        BillRequest billRequest = request(bill, "PAYMENT_INITIATION", "PAYMENT_APPROVER");

        enrichmentUtil.enrichBillSignatures(billRequest, billFromSearch);

        assertEquals(2, bill.getSignatures().size());
        assertEquals("sig-old", bill.getSignatures().get(0).getId());
        assertEquals("PAYMENT_APPROVER", bill.getSignatures().get(1).getRole());
    }

    @Test
    void skipsDuplicateSignatureIdsResentByClient() {
        BillSignature existing = BillSignature.builder()
                .id("sig-1").printedName("Jane").fileStoreId("fs-1").action("SEND_FOR_REVIEW").build();
        Bill billFromSearch = Bill.builder().signatures(Collections.singletonList(existing)).build();

        Bill bill = Bill.builder().signatures(new ArrayList<>(Collections.singletonList(
                BillSignature.builder().id("sig-1").printedName("Jane").fileStoreId("fs-1").build()))).build();
        BillRequest billRequest = request(bill, "SEND_FOR_REVIEW", "PAYMENT_EDITOR");

        enrichmentUtil.enrichBillSignatures(billRequest, billFromSearch);

        assertEquals(1, bill.getSignatures().size());
        assertEquals("sig-1", bill.getSignatures().get(0).getId());
    }

    @Test
    void keepsExistingAdditionalDetailsKeysWhenSyncing() {
        Bill bill = Bill.builder()
                .additionalDetails(objectMapper.createObjectNode().put("billingType", "INTERMEDIATE"))
                .signatures(new ArrayList<>(Collections.singletonList(
                        BillSignature.builder().printedName("Jane").fileStoreId("fs-1").build())))
                .build();
        BillRequest billRequest = request(bill, "SEND_FOR_REVIEW", "PAYMENT_EDITOR");

        enrichmentUtil.enrichBillSignatures(billRequest, null);

        JsonNode details = objectMapper.valueToTree(bill.getAdditionalDetails());
        assertEquals("INTERMEDIATE", details.get("billingType").asText());
        assertEquals(1, details.get("signatures").size());
    }

    @Test
    void noOpWhenNoSignaturesAnywhere() {
        Bill bill = Bill.builder().build();
        BillRequest billRequest = request(bill, "SEND_FOR_REVIEW", "PAYMENT_EDITOR");

        enrichmentUtil.enrichBillSignatures(billRequest, null);

        assertNull(bill.getSignatures());
        assertNull(bill.getAdditionalDetails());
    }
}
