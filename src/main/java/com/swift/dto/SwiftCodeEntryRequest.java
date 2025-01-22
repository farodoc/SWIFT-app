package com.swift.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swift.annotations.*;
import com.swift.util.SwiftCodeEntryRequestDeserializer;
import jakarta.validation.GroupSequence;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@GroupSequence({SwiftCodeEntryRequest.class, ValidationGroups.FieldChecks.class, ValidationGroups.ClassChecks.class})
@SwiftCodeWithIsHeadquarterConstraint(groups = ValidationGroups.ClassChecks.class)
@JsonDeserialize(using = SwiftCodeEntryRequestDeserializer.class)
public class SwiftCodeEntryRequest {
    @AddressConstraint(groups = ValidationGroups.FieldChecks.class)
    private String address;

    @BankNameConstraint(groups = ValidationGroups.FieldChecks.class)
    private String bankName;

    @CountryISO2Constraint(groups = ValidationGroups.FieldChecks.class)
    private String countryISO2;

    @CountryNameConstraint(groups = ValidationGroups.FieldChecks.class)
    private String countryName;

    private Boolean isHeadquarter;

    @SwiftCodeConstraint(groups = ValidationGroups.FieldChecks.class)
    private String swiftCode;
}
