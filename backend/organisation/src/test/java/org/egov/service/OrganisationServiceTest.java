package org.egov.service;

import org.egov.config.Configuration;
import org.egov.helper.OrganisationRequestTestBuilder;
import org.egov.kafka.Producer;
import org.egov.validator.OrganisationServiceValidator;
import org.egov.web.models.OrgRequest;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class OrganisationServiceTest {

    @InjectMocks
    private OrganisationService organisationService;

    @Mock
    private OrganisationServiceValidator organisationServiceValidator;

    @Mock
    private OrganisationEnrichmentService organisationEnrichmentService;

    @Mock
    private Configuration configuration;

    @Mock
    private Producer producer;

    @Mock
    private IndividualService userService;

    @Mock
    NotificationService notificationService;

    @Test
    public void shouldCreateOrganisationSuccessfully(){
        OrgRequest orgRequest = OrganisationRequestTestBuilder.builder().withRequestInfo().addGoodOrganisationForCreate().build();
        when(configuration.getOrgKafkaCreateTopic()).thenReturn("save-organisation");

        organisationService.createOrganisationWithoutWorkFlow(orgRequest);

        verify(organisationServiceValidator, times(1)).validateCreateOrgRegistryWithoutWorkFlow(orgRequest);

        verify(organisationEnrichmentService, times(1)).enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);

        verify(userService, times(1)).createIndividual(orgRequest);

        verify(producer, times(1)).push(eq("save-organisation"), any(OrgRequest.class));

        assertNotNull(orgRequest.getOrganisations());

    }
}
