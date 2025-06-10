package org.egov.service;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.config.Configuration;
import org.egov.helper.OrganisationRequestTestBuilder;
import org.egov.util.IdgenUtil;
import org.egov.util.OrganisationUtil;
import org.egov.web.models.ApplicationStatus;
import org.egov.web.models.OrgRequest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        getIdGenIds();
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
        assertNotNull(orgRequest.getOrganisations().get(0).getApplicationNumber());
        assertNotNull(orgRequest.getOrganisations().get(0).getOrgNumber());
        assertNotNull(orgRequest.getOrganisations().get(0).getFunctions().get(0).getApplicationNumber());
    }

    @Test
    public void shouldEnrichOrganisationId() {
        getIdGenIds();
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
        assertNotNull(orgRequest.getOrganisations().get(0).getId());
    }

    @Test
    public void shouldEnrichOrganisationAddressId() {
        getIdGenIds();
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
        assertNotNull(orgRequest.getOrganisations().get(0).getOrgAddress().get(0).getId());
    }

    @Test
    public void shouldEnrichOrganisationContactDetailsId() {
        getIdGenIds();
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
        assertNotNull(orgRequest.getOrganisations().get(0).getContactDetails().get(0).getId());
    }

    @Test
    public void shouldEnrichOrganisationJurisdictionId() {
        getIdGenIds();
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
        assertNotNull(orgRequest.getOrganisations().get(0).getJurisdiction().get(0).getId());
    }

    @Test
    public void shouldEnrichOrganisationDocumentsId() {
        getIdGenIds();
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
        assertNotNull(orgRequest.getOrganisations().get(0).getDocuments().get(0).getId());
    }

    @Test
    public void shouldEnrichOrganisationFunctionId() {
        getIdGenIds();
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
        assertNotNull(orgRequest.getOrganisations().get(0).getFunctions().get(0).getId());
    }

    @Test
    public void shouldEnrichOrganisationFunctionDocumentsId() {
        getIdGenIds();
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
        assertNotNull(orgRequest.getOrganisations().get(0).getFunctions().get(0).getDocuments().get(0).getId());
    }

    public void getIdGenIds() {
        when(configuration.getOrgApplicationNumberName()).thenReturn("organisation.application.number");
        when(configuration.getOrgApplicationNumberFormat()).thenReturn("OR/AP/001/001");
        when(configuration.getOrgNumberName()).thenReturn("organisation.number");
        when(configuration.getOrgNumberFormat()).thenReturn("OR/001/001");
        when(configuration.getFunctionApplicationNumberName()).thenReturn("organisation.function.number");
        when(configuration.getFunctionApplicationNumberFormat()).thenReturn("OR/FN/001/001");

        List<String> applicationNumbers = Collections.singletonList("OR/AP/001/001");
        List<String> orgNumbers = Collections.singletonList("OR/001/001");
        List<String> functionNumbers = Collections.singletonList("OR/FN/001/001");
        when(idgenUtil.getIdList(any(RequestInfo.class),any(String.class),eq("organisation.application.number"),any(String.class),anyInt()))
                .thenReturn(applicationNumbers);
        when(idgenUtil.getIdList(any(RequestInfo.class),any(String.class),eq("organisation.number"),any(String.class),anyInt()))
                .thenReturn(orgNumbers);
        when(idgenUtil.getIdList(any(RequestInfo.class),any(String.class),eq("organisation.function.number"),any(String.class),anyInt()))
                .thenReturn(functionNumbers);
    }

}
