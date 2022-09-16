package org.egov.works.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdResponse;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.LOIConfiguration;
import org.egov.works.repository.IdGenRepository;
import org.egov.works.util.LOIUtil;
import org.egov.works.web.models.LetterOfIndent;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LOIEnrichmentService {

    @Autowired
    private LOIUtil loiUtil;
    @Autowired
    private LOIConfiguration config;
    @Autowired
    private IdGenRepository idGenRepository;

    public void enrichLOI(LetterOfIndentRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        LetterOfIndent letterOfIndent = request.getLetterOfIndent();
        String rootTenantId = letterOfIndent.getTenantId().split("\\.")[0];

        letterOfIndent.setId(UUID.randomUUID());

        AuditDetails auditDetails = loiUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), letterOfIndent, true);
        letterOfIndent.setAuditDetails(auditDetails);

        List<String> loiNumbers = getIdList(requestInfo, rootTenantId, config.getIdGenLOINumberName(),
                config.getIdGenLOINumberFormat(), 1);

        if (loiNumbers != null && !loiNumbers.isEmpty()) {
            letterOfIndent.setLetterOfIndentNumber(loiNumbers.get(0));
        }
    }

    private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey, String idformat, int count) {
        List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count).getIdResponses();
        if (CollectionUtils.isEmpty(idResponses))
            throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");
        return idResponses.stream().map(IdResponse::getId).collect(Collectors.toList());
    }

}
