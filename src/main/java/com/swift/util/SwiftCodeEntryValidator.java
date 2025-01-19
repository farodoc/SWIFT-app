package com.swift.util;

import com.swift.dto.SwiftCodeEntryRequest;
import org.springframework.stereotype.Component;

@Component
public class SwiftCodeEntryValidator {

    public boolean isValidSwiftCode(String swiftCode) {
        return swiftCode.matches("^[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?$");
    }

    public boolean isValidCountryISO2(String countryISO2) {
        return countryISO2.matches("^[A-Z]{2}$");
    }

    public boolean isValidCountryName(String countryName) {
        return countryName.matches("^[A-Za-z ]+$");
    }

    public boolean isValidBankName(String bankName) {
        return bankName.matches("^[A-Za-z0-9 ]+$");
    }

    public boolean isValidAddress(String address) {
        return address.matches("^[A-Za-z0-9 ,.]+$");
    }

    public boolean isValidSwiftCodeEntryRequest(SwiftCodeEntryRequest swiftCodeEntryRequest) {
        return isValidSwiftCode(swiftCodeEntryRequest.getSwiftCode())
                && isValidBankName(swiftCodeEntryRequest.getBankName())
                && isValidCountryISO2(swiftCodeEntryRequest.getCountryISO2())
                && isValidCountryName(swiftCodeEntryRequest.getCountryName())
                && isValidAddress(swiftCodeEntryRequest.getAddress());
    }

}
