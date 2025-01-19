package com.swift.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swift.annotations.CountryISO2Constraint;
import com.swift.annotations.SwiftCodeConstraint;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SwiftCodeEntryRequest {

    private String address;
    private String bankName;

    @CountryISO2Constraint
    private String countryISO2;

    private String countryName;
    @JsonProperty("isHeadquarter")
    private boolean headquarter;

    @SwiftCodeConstraint
    private String swiftCode;

}
