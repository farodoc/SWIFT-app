package com.swift.util;

import com.swift.dto.BankBranchDto;
import com.swift.dto.BankHqDto;
import com.swift.dto.HqsBranchBank;
import com.swift.dto.SwiftCodeEntryRequest;
import com.swift.model.BankBranch;
import com.swift.model.BankHq;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BankMapper {
    public Optional<BankBranchDto> convertBranchToDto(Optional<BankBranch> bankBranch) {
        if (bankBranch.isEmpty()) {
            return Optional.empty();
        }

        BankBranch bank = bankBranch.get();

        return Optional.of(
                BankBranchDto.builder()
                        .address(bank.getAddress())
                        .bankName(bank.getBankName())
                        .countryISO2(bank.getCountryISO2())
                        .countryName(bank.getCountryName())
                        .isHeadquarter(false)
                        .swiftCode(bank.getSwiftCode())
                        .build()
        );
    }

    public Optional<BankHqDto> convertHqToDto(Optional<BankHq> bankHq) {
        if (bankHq.isEmpty()) {
            return Optional.empty();
        }

        BankHq bank = bankHq.get();

        return Optional.of(
                BankHqDto.builder()
                        .address(bank.getAddress())
                        .bankName(bank.getBankName())
                        .countryISO2(bank.getCountryISO2())
                        .countryName(bank.getCountryName())
                        .isHeadquarter(true)
                        .swiftCode(bank.getSwiftCode())
                        .branches(bank.getBankBranches().stream().map(this::convertToHqsBranchBank).toList())
                        .build()
        );
    }

    public HqsBranchBank convertToHqsBranchBank(BankBranch bankBranch) {
        return HqsBranchBank.builder()
                .address(bankBranch.getAddress())
                .bankName(bankBranch.getBankName())
                .countryISO2(bankBranch.getCountryISO2())
                .isHeadquarter(false)
                .swiftCode(bankBranch.getSwiftCode())
                .build();
    }

    public HqsBranchBank convertToHqsBranchBank(BankHq bankHq) {
        return HqsBranchBank.builder()
                .address(bankHq.getAddress())
                .bankName(bankHq.getBankName())
                .countryISO2(bankHq.getCountryISO2())
                .isHeadquarter(true)
                .swiftCode(bankHq.getSwiftCode())
                .build();
    }

    public BankHq convertToBankHq(SwiftCodeEntryRequest swiftCodeEntryRequest) {
        return BankHq.builder()
                .swiftCode(swiftCodeEntryRequest.getSwiftCode())
                .bankName(swiftCodeEntryRequest.getBankName())
                .address(swiftCodeEntryRequest.getAddress())
                .countryISO2(swiftCodeEntryRequest.getCountryISO2())
                .countryName(swiftCodeEntryRequest.getCountryName())
                .build();
    }

    public BankBranch convertToBankBranch(SwiftCodeEntryRequest swiftCodeEntryRequest) {
        return BankBranch.builder()
                .swiftCode(swiftCodeEntryRequest.getSwiftCode())
                .bankName(swiftCodeEntryRequest.getBankName())
                .address(swiftCodeEntryRequest.getAddress())
                .countryISO2(swiftCodeEntryRequest.getCountryISO2())
                .countryName(swiftCodeEntryRequest.getCountryName())
                .build();
    }
}
