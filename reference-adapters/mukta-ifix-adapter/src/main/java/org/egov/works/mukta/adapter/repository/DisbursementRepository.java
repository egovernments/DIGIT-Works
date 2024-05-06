package org.egov.works.mukta.adapter.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.repository.querybuilder.DisbursementQueryBuilder;
import org.egov.works.mukta.adapter.repository.rowmapper.DisbursementRowMapper;
import org.egov.works.mukta.adapter.util.EncryptionDecryptionUtil;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.mukta.adapter.web.models.DisbursementSearchCriteria;
import org.egov.works.mukta.adapter.web.models.DisbursementSearchRequest;
import org.egov.works.mukta.adapter.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class DisbursementRepository {
    private final DisbursementQueryBuilder disbursementQueryBuilder;
    private final DisbursementRowMapper disbursementRowMapper;
    private final EncryptionDecryptionUtil encryptionDecryptionUtil;
    private final MuktaAdaptorConfig muktaAdaptorConfig;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DisbursementRepository(DisbursementQueryBuilder disbursementQueryBuilder, DisbursementRowMapper disbursementRowMapper, EncryptionDecryptionUtil encryptionDecryptionUtil, MuktaAdaptorConfig muktaAdaptorConfig, JdbcTemplate jdbcTemplate) {
        this.disbursementQueryBuilder = disbursementQueryBuilder;
        this.disbursementRowMapper = disbursementRowMapper;
        this.encryptionDecryptionUtil = encryptionDecryptionUtil;
        this.muktaAdaptorConfig = muktaAdaptorConfig;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Disbursement> searchDisbursement(DisbursementSearchRequest disbursementSearchRequest) {
        log.info("DisbursementSearchRequest: " + disbursementSearchRequest.toString());
        List<Object> preparedStmtList = new ArrayList<>();
        String query = disbursementQueryBuilder.getDisbursementSearchQuery(disbursementSearchRequest, preparedStmtList,null,true);
        List<Disbursement> disbursements = jdbcTemplate.query(query, preparedStmtList.toArray(), disbursementRowMapper);
        List<String> parentIds = new ArrayList<>();
        if (disbursements != null) {
            for (Disbursement disbursement : disbursements) {
                if (disbursement.getId() != null) {
                    parentIds.add(disbursement.getId());
                }
            }
        }
        if(!parentIds.isEmpty()){
            enrichedDisbursements(parentIds, disbursements);
        }
        disbursements.replaceAll(disbursement -> encryptionDecryptionUtil.decryptObject(disbursement, muktaAdaptorConfig.getMuktaAdapterEncryptionKey(), Disbursement.class, disbursementSearchRequest.getRequestInfo()));
        return disbursements;
    }

    private void enrichedDisbursements(List<String> parentIds, List<Disbursement> disbursements) {
        List<Object> preparedStmtList = new ArrayList<>();
        DisbursementSearchRequest disbursementSearchCriteriaForChildrens = DisbursementSearchRequest.builder()
                .criteria(DisbursementSearchCriteria.builder().build())
                .pagination(Pagination.builder().build())
                .build();
        String query = disbursementQueryBuilder.getDisbursementSearchQuery(disbursementSearchCriteriaForChildrens, preparedStmtList,parentIds,false);
        List<Disbursement> disbursementsForChildrens = jdbcTemplate.query(query, preparedStmtList.toArray(), disbursementRowMapper);
        if(disbursementsForChildrens == null || disbursementsForChildrens.isEmpty()){
            log.info("No childrens found for disbursements");
        }else{
            HashMap<String, List<Disbursement>> disbursementHashMap = new HashMap<>();
            for(Disbursement disbursement : disbursementsForChildrens){
                if(disbursementHashMap.containsKey(disbursement.getParentId())){
                    disbursementHashMap.get(disbursement.getParentId()).add(disbursement);
                }else{
                    List<Disbursement> disbursements1 = new ArrayList<>();
                    disbursements1.add(disbursement);
                    disbursementHashMap.put(disbursement.getParentId(),disbursements1);
                }
            }

            for(Disbursement disbursement : disbursements){
                if(disbursementHashMap.containsKey(disbursement.getId())){
                    disbursement.setDisbursements(disbursementHashMap.get(disbursement.getId()));
                }
            }
        }
    }
}
