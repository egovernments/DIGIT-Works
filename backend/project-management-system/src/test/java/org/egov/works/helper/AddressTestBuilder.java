package org.egov.works.helper;


import org.egov.works.web.models.Address;

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
                .id("Id-1")
                .tenantId("state.city")
                .doorNo("DoorNo-1")
                .latitude(Double.valueOf(1.1))
                .longitude(Double.valueOf(1.1))
                .locationAccuracy(Double.valueOf(1.1))
                .type("Type-1")
                .addressLine1("AddressLine1-1")
                .addressLine2("addressLine2-1")
                .landmark("Landmark-1")
                .city("City-1")
                .pincode("Pincode-1")
                .buildingName("BuildingName-1")
                .street("Street-1")
                .boundaryType("Locality")
                .boundary("Locality-2")
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build());
        return this;
    }

    public AddressTestBuilder addAddressWithoutIdAndAuditDetails(){
        this.builder
                .tenantId("state.city")
                .doorNo("DoorNo-1")
                .latitude(Double.valueOf(1.1))
                .longitude(Double.valueOf(1.1))
                .locationAccuracy(Double.valueOf(1.1))
                .type("Type-1")
                .addressLine1("AddressLine1-1")
                .addressLine2("addressLine2-1")
                .landmark("Landmark-1")
                .city("City-1")
                .pincode("Pincode-1")
                .buildingName("BuildingName-1")
                .street("Street-1")
                .boundaryType("Locality")
                .boundary("Locality-1");
        return this;
    }
}
