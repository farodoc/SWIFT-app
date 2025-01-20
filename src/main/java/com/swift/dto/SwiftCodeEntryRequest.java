package com.swift.dto;

import com.swift.annotations.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
