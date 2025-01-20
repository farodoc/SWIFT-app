package com.swift.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swift.annotations.*;
import com.swift.util.SwiftCodeEntryRequestDeserializer;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SwiftCodeWithIsHeadquarterConstraint
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

    @NotNull
    private Boolean isHeadquarter;

    @SwiftCodeConstraint
    private String swiftCode;

}
