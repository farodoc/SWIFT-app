package com.swift.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swift.annotations.*;
import com.swift.util.SwiftCodeEntryRequestDeserializer;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonDeserialize(using = SwiftCodeEntryRequestDeserializer.class)
public class SwiftCodeEntryRequest {

    @AddressConstraint
    private String address;

    @BankNameConstraint
    private String bankName;

    @CountryISO2Constraint
    private String countryISO2;

    @CountryNameConstraint
    private String countryName;

    private Boolean isHeadquarter;

    @SwiftCodeConstraint
    private String swiftCode;

}
