package org.egov.helper;


import org.egov.web.models.Address;
import org.egov.web.models.GeoLocation;

public class AddressTestBuilder {

    private Address.AddressBuilder builder;

    public AddressTestBuilder(){
        this.builder = Address.builder();
    }

    public static AddressTestBuilder builder(){
        return new AddressTestBuilder();
    }

    public Address build(){
        return this.builder.build();
    }

    public AddressTestBuilder addGoodAddress(){
        this.builder
                .id("address-id-1")
                .tenantId("state.city")
                .orgId("org-id-1")
                .doorNo("DoorNo-1")
                .plotNo("plot-no")
                .landmark("Landmark-1")
                .city("City-1")
                .pincode("PinCode-1")
                .district("District-1")
                .region("Region-1")
                .state("State-1")
                .country("Country-1")
                .additionDetails(null)
                .buildingName("Building-1")
                .street("Street-1")
                .boundaryType("Ward")
                .boundaryCode("B1")
                .geoLocation(getGeoLocation());
        return this;
    }

    public AddressTestBuilder addGoodAddressWithoutId(){
        this.builder
                .tenantId("state.city")
                .orgId("org-id-1")
                .doorNo("DoorNo-1")
                .plotNo("plot-no")
                .landmark("Landmark-1")
                .city("City-1")
                .pincode("PinCode-1")
                .district("District-1")
                .region("Region-1")
                .state("State-1")
                .country("Country-1")
                .additionDetails(null)
                .buildingName("Building-1")
                .street("Street-1")
                .boundaryType("Ward")
                .boundaryCode("B1")
                .geoLocation(getGeoLocation());
        return this;
    }

    private GeoLocation getGeoLocation() {
        GeoLocation geoLocation = GeoLocation.builder()
                .id("gl-id-1")
                .addressId("address-id-1")
                .longitude(null)
                .latitude(null)
                .additionalDetails(null)
                .build();
        return geoLocation;
    }

}
