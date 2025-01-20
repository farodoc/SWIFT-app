package com.swift.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BankBranchDto {

    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private Boolean isHeadquarter;
    private String swiftCode;

}
