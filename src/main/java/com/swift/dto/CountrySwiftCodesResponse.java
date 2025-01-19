package com.swift.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountrySwiftCodesResponse {

    private String countryISO2;
    private String countryName;
    private List<HqsBranchBank> swiftCodes;

}
