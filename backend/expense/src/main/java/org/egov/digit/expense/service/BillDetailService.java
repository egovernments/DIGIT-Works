package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillDetailRepository;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BillDetailService {

    private final BillDetailRepository repository;
    private final BillDetailCacheService cacheService;
    private final ExpenseProducer producer;
    private final Configuration config;

    @Autowired
    public BillDetailService(BillDetailRepository repository,
                              BillDetailCacheService cacheService,
                              ExpenseProducer producer,
                              Configuration config) {
        this.repository = repository;
        this.cacheService = cacheService;
        this.producer = producer;
        this.config = config;
    }

    public void create(List<BillDetail> details, String tenantId) {
        if (details == null || details.isEmpty()) return;
        for (BillDetail detail : details) {
            BillDetailMessage msg = BillDetailMessage.builder()
                    .billDetail(detail)
                    .action("CREATE")
                    .build();
            producer.push(tenantId, config.getBillDetailCreateTopic(), msg);
            log.debug("Pushed detail create id={} to topic={}", detail.getId(), config.getBillDetailCreateTopic());
        }
    }

    public void update(List<BillDetail> details, String tenantId) {
        if (details == null || details.isEmpty()) return;
        for (BillDetail detail : details) {
            cacheService.putDetail(detail, tenantId);
            BillDetailMessage msg = BillDetailMessage.builder()
                    .billDetail(detail)
                    .action("UPDATE")
                    .build();
            producer.push(tenantId, config.getBillDetailUpdateTopic(), msg);
            log.debug("Pushed detail update id={} to topic={}", detail.getId(), config.getBillDetailUpdateTopic());
        }
    }

    /**
     * Returns all details for the given bill IDs.
     * Strategy: check billDetailIds cache per bill → get each detail from detail cache → DB fallback.
     * Populates caches for any DB hits.
     */
    public List<BillDetail> searchByBillIds(List<String> billIds, String tenantId) {
        if (billIds == null || billIds.isEmpty()) return Collections.emptyList();

        List<BillDetail> result = new ArrayList<>();
        Set<String> billIdsNeedingDb = new HashSet<>();

        for (String billId : billIds) {
            Optional<List<String>> cachedIds = cacheService.getDetailIds(billId, tenantId);
            if (cachedIds.isPresent()) {
                List<String> detailIds = cachedIds.get();
                List<String> uncachedDetailIds = new ArrayList<>();
                for (String detailId : detailIds) {
                    Optional<BillDetail> cached = cacheService.getDetail(billId, detailId, tenantId);
                    if (cached.isPresent()) {
                        result.add(cached.get());
                    } else {
                        uncachedDetailIds.add(detailId);
                    }
                }
                if (!uncachedDetailIds.isEmpty()) {
                    List<BillDetail> fromDb = repository.searchByDetailIds(uncachedDetailIds, tenantId);
                    fromDb.forEach(d -> cacheService.putDetail(d, tenantId));
                    result.addAll(fromDb);
                }
            } else {
                billIdsNeedingDb.add(billId);
            }
        }

        if (!billIdsNeedingDb.isEmpty()) {
            List<BillDetail> fromDb = repository.searchByBillIds(new ArrayList<>(billIdsNeedingDb), tenantId);
            Map<String, List<String>> idsByBill = new HashMap<>();
            for (BillDetail d : fromDb) {
                cacheService.putDetail(d, tenantId);
                idsByBill.computeIfAbsent(d.getBillId(), k -> new ArrayList<>()).add(d.getId());
            }
            for (String billId : billIdsNeedingDb) {
                cacheService.putDetailIds(billId, tenantId,
                        idsByBill.getOrDefault(billId, Collections.emptyList()));
            }
            result.addAll(fromDb);
        }

        return result;
    }

    /**
     * Enrich a list of bills with their details from cache/DB and overlay detail cache for freshness.
     */
    public void enrichBillsWithDetails(List<org.egov.digit.expense.web.models.Bill> bills, String tenantId) {
        if (bills == null || bills.isEmpty()) return;
        List<String> billIds = bills.stream()
                .map(org.egov.digit.expense.web.models.Bill::getId)
                .collect(Collectors.toList());
        List<BillDetail> allDetails = searchByBillIds(billIds, tenantId);
        Map<String, List<BillDetail>> byBillId = allDetails.stream()
                .collect(Collectors.groupingBy(BillDetail::getBillId));
        for (org.egov.digit.expense.web.models.Bill bill : bills) {
            bill.setBillDetails(byBillId.getOrDefault(bill.getId(), Collections.emptyList()));
        }
    }
}
