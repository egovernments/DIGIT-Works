package org.egov.service;

import digit.models.coremodels.IdGenerationResponse;
import digit.models.coremodels.IdResponse;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.config.Configuration;
import org.egov.helper.OrganisationRequestTestBuilder;
import org.egov.util.IdgenUtil;
import org.egov.util.OrganisationUtil;
import org.egov.web.models.OrgRequest;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class OrganisationEnrichmentServiceTest {

    @InjectMocks
    private OrganisationEnrichmentService organisationEnrichmentService;

    @Mock
    private OrganisationUtil organisationUtil;

    @Mock
    private Configuration configuration;

    @Mock
    private IdgenUtil idgenUtil;

    @Test
    public void shouldGenerateOrganisationNumber_IfSuccess() {
//        when(configuration.getOrgApplicationNumberName()).thenReturn("organisation.application.number");
//        when(configuration.getOrgApplicationNumberFormat()).thenReturn("OR/AP/001/001");
//        List<String> applicationNumbers = Arrays.asList("OR/AP/001/001");
//        when(idgenUtil.getIdList(any(RequestInfo.class),any(String.class),eq("organisation.application.number"),any(String.class),anyInt()))
//                .thenReturn(applicationNumbers);
//        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
////        orgRequest.getOrganisations().get(0).setTenantId("t1");
//
//        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
//        assertNotNull(orgRequest.getOrganisations().get(0).getApplicationNumber());
    }

}
