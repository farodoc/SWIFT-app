package com.swift.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HqsBranchBank {

    private String address;
    private String bankName;
    private String countryISO2;
    private Boolean isHeadquarter;
    private String swiftCode;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HqsBranchBank that = (HqsBranchBank) obj;
        return address.equals(that.address) && bankName.equals(that.bankName) && countryISO2.equals(that.countryISO2) && isHeadquarter.equals(that.isHeadquarter) && swiftCode.equals(that.swiftCode);
    }

}
