package com.swift.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BankHqDto {

    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private Boolean isHeadquarter;
    private String swiftCode;
    private List<HqsBranchBank> branches;

}
