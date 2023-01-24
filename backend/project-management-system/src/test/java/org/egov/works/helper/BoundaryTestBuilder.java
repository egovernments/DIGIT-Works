package org.egov.works.helper;

import org.egov.works.web.models.Boundary;

import java.util.Collections;

public class BoundaryTestBuilder {

    private Boundary.BoundaryBuilder builder;

    public BoundaryTestBuilder (){
        this.builder = Boundary.builder();
    }

    public static BoundaryTestBuilder builder(){
        return new BoundaryTestBuilder();
    }

    public Boundary build(){
        return this.builder.build();
    }

    public BoundaryTestBuilder addGoodBoundary(){
       Boundary boundary = addGoodBoundaryWithOutChildren().build();
       this.builder.children(Collections.singletonList(boundary));
       return this;
    }

    public BoundaryTestBuilder addGoodBoundaryWithOutChildren(){
        this.builder
                .id("Id-1")
                .parentid("ParentId-1")
                .addressid("AddressId-1")
                .code("Code-1")
                .name("Name-1")
                .label("Label")
                .latitude("Latitude-1")
                .longitude("Longitude-1")
                .materializedPath("MaterializedPath-1");
        return this;
    }

}
