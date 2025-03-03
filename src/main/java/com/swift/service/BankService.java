package com.swift.service;

import com.swift.dto.CountrySwiftCodesResponse;
import com.swift.dto.HqsBranchBank;
import com.swift.dto.SwiftCodeEntryRequest;
import com.swift.model.BankBranch;
import com.swift.model.BankHq;
import com.swift.repository.BankBranchRepository;
import com.swift.repository.BankHqRepository;
import com.swift.util.BankMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.swift.config.Config.BANK_HQ_SUFFIX;
import static com.swift.config.Config.BANK_SWIFT_CODE_PREFIX_LENGTH;

@Service
public class BankService {
    private final BankHqRepository bankHqRepository;
    private final BankBranchRepository bankBranchRepository;
    private final BankMapper bankMapper;
    private final EntityManager entityManager;

    public BankService(BankHqRepository bankHqRepository, BankBranchRepository bankBranchRepository, BankMapper bankMapper, EntityManager entityManager) {
        this.bankHqRepository = bankHqRepository;
        this.bankBranchRepository = bankBranchRepository;
        this.bankMapper = bankMapper;
        this.entityManager = entityManager;
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

        String countryName;

        if (!bankBranches.isEmpty()) {
            countryName = bankBranches.getFirst().getCountryName();
        } else if (!bankHqs.isEmpty()) {
            countryName = bankHqs.getFirst().getCountryName();
        } else {
            countryName = null;
        }

        List<HqsBranchBank> swiftCodes = new ArrayList<>();

        swiftCodes.addAll(bankBranches.stream().map(bankMapper::convertToHqsBranchBank).toList());
        swiftCodes.addAll(bankHqs.stream().map(bankMapper::convertToHqsBranchBank).toList());

        return CountrySwiftCodesResponse.builder()
                .countryISO2(countryISO2)
                .countryName(countryName)
                .swiftCodes(swiftCodes)
                .build();
    }

    @Transactional
    public void createBank(SwiftCodeEntryRequest swiftCodeEntryRequest) {
        String swiftCode = swiftCodeEntryRequest.getSwiftCode();
        boolean exists = swiftCode.endsWith(BANK_HQ_SUFFIX)
                ? bankHqRepository.findBySwiftCode(swiftCode).isPresent()
                : bankBranchRepository.findBySwiftCode(swiftCode).isPresent();

        if (exists) {
            throw new IllegalArgumentException("Bank with the given SWIFT code already exists");
        }

        if (swiftCodeEntryRequest.getIsHeadquarter()) {
            BankHq bankHq = bankMapper.convertToBankHq(swiftCodeEntryRequest);

            List<BankBranch> bankBranches = bankBranchRepository.findBySwiftCodeStartingWith(bankHq.getSwiftCode().substring(0, BANK_SWIFT_CODE_PREFIX_LENGTH));

            bankBranches.forEach(bankBranch -> bankBranch.setBankHq(bankHq));
            bankHq.setBankBranches(bankBranches);

            bankHqRepository.save(bankHq);
        } else {
            BankBranch bankBranch = bankMapper.convertToBankBranch(swiftCodeEntryRequest);

            Optional<BankHq> bankHq = bankHqRepository.findBySwiftCode(bankBranch.getSwiftCode().substring(0, BANK_SWIFT_CODE_PREFIX_LENGTH) + BANK_HQ_SUFFIX);

            bankBranch.setBankHq(bankHq.orElse(null));
            bankHq.ifPresent(hq -> hq.getBankBranches().add(bankBranch));

            bankBranchRepository.findBySwiftCode(bankBranch.getSwiftCode()).ifPresent(entityManager::detach);

            bankBranchRepository.save(bankBranch);
        }
    }

    @Transactional
    public void deleteBank(String swiftCode) {
        if (swiftCode.endsWith(BANK_HQ_SUFFIX)) {
            bankHqRepository.deleteBySwiftCode(swiftCode);
        } else {
            bankBranchRepository.deleteBySwiftCode(swiftCode);
        }
    }
}
