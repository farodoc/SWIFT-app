package com.swift.service;

import com.swift.dto.CountrySwiftCodesResponse;
import com.swift.dto.HqsBranchBank;
import com.swift.model.BankBranch;
import com.swift.model.BankHq;
import com.swift.repository.BankBranchRepository;
import com.swift.repository.BankHqRepository;
import com.swift.util.BankMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private final BankHqRepository bankHqRepository;
    private final BankBranchRepository bankBranchRepository;
    private final BankMapper bankMapper;
    private final static String BANK_HQ_SUFFIX = "XXX";

    public BankService(BankHqRepository bankHqRepository, BankBranchRepository bankBranchRepository, BankMapper bankMapper) {
        this.bankHqRepository = bankHqRepository;
        this.bankBranchRepository = bankBranchRepository;
        this.bankMapper = bankMapper;
    }

    public Optional<?> getBank(String swiftCode) {
        if (swiftCode.endsWith(BANK_HQ_SUFFIX)) {
            Optional<BankHq> bankHq = bankHqRepository.findBySwiftCode(swiftCode);

            return bankMapper.convertHqToDto(bankHq);
        } else {
            Optional<BankBranch> bankBranch = bankBranchRepository.findBySwiftCode(swiftCode);

            return bankMapper.convertBranchToDto(bankBranch);
        }
    }

    public CountrySwiftCodesResponse getBanksByCountry(String countryISO2) {
        List<BankBranch> bankBranches = bankBranchRepository.findByCountryISO2(countryISO2);
        List<BankHq> bankHqs = bankHqRepository.findByCountryISO2(countryISO2);

        List<HqsBranchBank> swiftCodes = new ArrayList<>();

        swiftCodes.addAll(bankBranches.stream().map(bankMapper::convertToHqsBranchBank).toList());
        swiftCodes.addAll(bankHqs.stream().map(bankMapper::convertToHqsBranchBank).toList());

        return CountrySwiftCodesResponse.builder()
                .countryISO2(countryISO2)
                .countryName("XXXXXXXXXXXXXXXXX")
                .swiftCodes(swiftCodes)
                .build();
    }

}
