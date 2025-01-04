package org.egov.util;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.http.client.ServiceRequestClient;
import org.egov.common.models.Error;
import org.egov.common.models.core.Boundary;
import org.egov.common.models.household.Household;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.egov.web.models.boundary.BoundaryResponse;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.egov.common.utils.CommonUtils.populateErrorDetails;

@Component
@Slf4j
public class BoundaryServiceUtil {

	private final ServiceRequestClient serviceRequestClient;
	private final AttendanceServiceConfiguration attendanceServiceConfiguration;

	public BoundaryServiceUtil(ServiceRequestClient serviceRequestClient, AttendanceServiceConfiguration attendanceServiceConfiguration) {
		this.serviceRequestClient = serviceRequestClient;
		this.attendanceServiceConfiguration = attendanceServiceConfiguration;
	}


	public <R,T> void validateLocalityCode(
		R request,
		List<T> entities,
		Function<T, String> tenantIdGetter,
		Function<T, String> localityCodeGetter,
		Map<String, String> errorMap
	) {
		log.debug("Validating boundaries for entities.");

		// Filter entities with non-null locality codes
		List<T> entitiesWithValidLocalities = entities.stream()
			.filter(entity -> Objects.nonNull(localityCodeGetter.apply(entity)))
			.toList();

		if (!entitiesWithValidLocalities.isEmpty()) {
			Map<String, List<T>> tenantIdEntityMap = entitiesWithValidLocalities.stream()
				.collect(Collectors.groupingBy(tenantIdGetter));

			tenantIdEntityMap.forEach((tenantId, entityGroup) -> {
				// Group entities by locality code
				Map<String, List<T>> localityCodeEntityMap = entityGroup.stream()
					.collect(Collectors.groupingBy(localityCodeGetter));

				List<String> localityCodes = new ArrayList<>(localityCodeEntityMap.keySet());

				try {
					// Fetch Boundary details from service
					log.debug("Fetching boundary details for tenantId: {}, locality codes: {}", tenantId, localityCodes);

					BoundaryResponse boundarySearchResponse = serviceRequestClient.fetchResult(
						new StringBuilder(attendanceServiceConfiguration.getBoundaryServiceHost()
							+ attendanceServiceConfiguration.getBoundarySearchUrl()
							+ "?limit=" + localityCodes.size()
							+ "&offset=0&tenantId=" + tenantId
							+ "&codes=" + String.join(",", localityCodes)),
						request,
						BoundaryResponse.class
					);

					log.debug("Boundary details fetched successfully for tenantId: {}", tenantId);

					List<String> invalidLocalityCodes = new ArrayList<>(localityCodes);
					invalidLocalityCodes.removeAll(boundarySearchResponse.getBoundary().stream()
						.map(Boundary::getCode)
						.toList());


					// Filter out attendance register with invalid locality codes
					List<T> registerWithInvalidBoundaries = localityCodeEntityMap.entrySet().stream()
						.filter(entry -> invalidLocalityCodes.contains(entry.getKey())) // filter invalid boundary codes
						.flatMap(entry -> entry.getValue().stream()) // Flatten the list of households
						.toList();

					registerWithInvalidBoundaries.forEach(register -> {
						// Populate error details for the attendance register
						errorMap.put("INVALID_LOCALITY_CODE", "Boundary Code does not exist");
					});

				} catch (Exception e) {
					log.error("Exception while searching boundaries for tenantId: {}", tenantId, e);
					throw new CustomException("BOUNDARY_SERVICE_SEARCH_ERROR", "Error while fetching boundaries from Boundary Service: " + e.getMessage());
				}
			});
		}
	}
}
