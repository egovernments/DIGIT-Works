package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.Address;
import org.egov.web.models.GeoLocation;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AddressRowMapper implements ResultSetExtractor<List<Address>> {

    private final ObjectMapper mapper;

    @Autowired
    public AddressRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Address> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<String, Address> addressMap = new LinkedHashMap<>();

        while (resultSet.next()) {
            String addressId = resultSet.getString("address_id");

            if (!addressMap.containsKey(addressId)) {
                addressMap.put(addressId, createAddressObject(resultSet));
            }
        }

        return new ArrayList<>(addressMap.values());
    }

    private Address createAddressObject(ResultSet rs) throws SQLException, DataAccessException  {
        GeoLocation geoLocation = createGeoLocationObjFromResultSet(rs);

        String addressId = rs.getString("address_id");
        String addressTenantId = rs.getString("address_tenantId");
        String addressOrgId = rs.getString("address_orgId");
        String addressDoorNo = rs.getString("address_doorNo");
        String addressPlotNo = rs.getString("address_plotNo");
        String addressLandmark = rs.getString("address_landmark");
        String addressCity = rs.getString("address_city");
        String addressPinCode = rs.getString("address_pinCode");
        String addressDistrict = rs.getString("address_district");
        String addressRegion = rs.getString("address_region");
        String addressState = rs.getString("address_state");
        String addressCountry = rs.getString("address_country");
        String addressBoundaryCode = rs.getString("address_boundaryCode");
        String addressBoundaryType = rs.getString("address_boundaryType");
        String addressBuildingName = rs.getString("address_buildingName");
        String addressStreet = rs.getString("address_street");
        JsonNode addressAdditionalDetails = getAdditionalDetail("address_additionalDetails", rs);

        return Address.builder()
                .id(addressId)
                .tenantId(addressTenantId)
                .orgId(addressOrgId)
                .doorNo(addressDoorNo)
                .plotNo(addressPlotNo)
                .landmark(addressLandmark)
                .city(addressCity)
                .pincode(addressPinCode)
                .district(addressDistrict)
                .region(addressRegion)
                .state(addressState)
                .country(addressCountry)
                .boundaryCode(addressBoundaryCode)
                .boundaryType(addressBoundaryType)
                .buildingName(addressBuildingName)
                .street(addressStreet)
                .additionDetails(addressAdditionalDetails)
                .geoLocation(geoLocation)
                .build();
    }

    private GeoLocation createGeoLocationObjFromResultSet(ResultSet rs)  throws SQLException, DataAccessException {
        String addressGeoLocationId = rs.getString("addressGeoLocation_Id");
        String addressGeoLocationAddressId = rs.getString("addressGeoLocation_addressId");
        Double addressGeoLocationLatitude = rs.getDouble("addressGeoLocation_latitude");
        Double addressGeoLocationLongitude = rs.getDouble("addressGeoLocation_longitude");
        JsonNode addressGeoLocationAdditionalDetails = getAdditionalDetail("addressGeoLocation_additionalDetails", rs);

        return GeoLocation.builder()
                .id(addressGeoLocationId)
                .addressId(addressGeoLocationAddressId)
                .latitude(addressGeoLocationLatitude)
                .longitude(addressGeoLocationLongitude)
                .additionalDetails(addressGeoLocationAdditionalDetails)
                .build();
    }

    private JsonNode getAdditionalDetail(String columnName, ResultSet rs) throws SQLException {
        JsonNode additionalDetails = null;
        try {
            PGobject obj = (PGobject) rs.getObject(columnName);
            if (obj != null) {
                additionalDetails = mapper.readTree(obj.getValue());
            }
        } catch (IOException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails != null && additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }

}
