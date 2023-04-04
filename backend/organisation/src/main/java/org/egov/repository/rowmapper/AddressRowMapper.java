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

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Address> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<String, Address> addressMap = new LinkedHashMap<>();

        while (resultSet.next()) {
            String address_id = resultSet.getString("address_id");

            if (!addressMap.containsKey(address_id)) {
                addressMap.put(address_id, createAddressObject(resultSet));
            }
        }

        return new ArrayList<>(addressMap.values());
    }

    private Address createAddressObject(ResultSet rs) throws SQLException, DataAccessException  {
        GeoLocation geoLocation = createGeoLocationObjFromResultSet(rs);

        String address_id = rs.getString("address_id");
        String address_tenantId = rs.getString("address_tenantId");
        String address_orgId = rs.getString("address_orgId");
        String address_doorNo = rs.getString("address_doorNo");
        String address_plotNo = rs.getString("address_plotNo");
        String address_landmark = rs.getString("address_landmark");
        String address_city = rs.getString("address_city");
        String address_pinCode = rs.getString("address_pinCode");
        String address_district = rs.getString("address_district");
        String address_region = rs.getString("address_region");
        String address_state = rs.getString("address_state");
        String address_country = rs.getString("address_country");
        String address_boundaryCode = rs.getString("address_boundaryCode");
        String address_boundaryType = rs.getString("address_boundaryType");
        String address_buildingName = rs.getString("address_buildingName");
        String address_street = rs.getString("address_street");
        JsonNode address_additionalDetails = getAdditionalDetail("address_additionalDetails", rs);

        Address address = Address.builder()
                .id(address_id)
                .tenantId(address_tenantId)
                .orgId(address_orgId)
                .doorNo(address_doorNo)
                .plotNo(address_plotNo)
                .landmark(address_landmark)
                .city(address_city)
                .pincode(address_pinCode)
                .district(address_district)
                .region(address_region)
                .state(address_state)
                .country(address_country)
                .boundaryCode(address_boundaryCode)
                .boundaryType(address_boundaryType)
                .buildingName(address_buildingName)
                .street(address_street)
                .additionDetails(address_additionalDetails)
                .geoLocation(geoLocation)
                .build();

        return address;
    }

    private GeoLocation createGeoLocationObjFromResultSet(ResultSet rs)  throws SQLException, DataAccessException {
        String addressGeoLocation_Id = rs.getString("addressGeoLocation_Id");
        String addressGeoLocation_addressId = rs.getString("addressGeoLocation_addressId");
        Double addressGeoLocation_latitude = rs.getDouble("addressGeoLocation_latitude");
        Double addressGeoLocation_longitude = rs.getDouble("addressGeoLocation_longitude");
        JsonNode addressGeoLocation_additionalDetails = getAdditionalDetail("addressGeoLocation_additionalDetails", rs);

        GeoLocation geoLocation = GeoLocation.builder()
                .id(addressGeoLocation_Id)
                .addressId(addressGeoLocation_addressId)
                .latitude(addressGeoLocation_latitude)
                .longitude(addressGeoLocation_longitude)
                .additionalDetails(addressGeoLocation_additionalDetails)
                .build();

        return geoLocation;
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
