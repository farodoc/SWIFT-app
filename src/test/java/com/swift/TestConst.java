package com.swift;

import com.swift.model.BankBranch;
import com.swift.model.BankHq;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("dev")
public class TestConst {
    private final static BankBranch BANK_BRANCH_1 = BankBranch.builder()
            .address("456 Main St")
            .bankName("Test Bank US")
            .countryISO2("US")
            .countryName("UNITED STATES")
            .swiftCode("TESTUSBR001")
            .build();

    private final static BankBranch BANK_BRANCH_2 = BankBranch.builder()
            .address("789 Main St")
            .bankName("Test Bank US")
            .countryISO2("US")
            .countryName("UNITED STATES")
            .swiftCode("TESTPLBR002")
            .build();

    private final static BankBranch BANK_BRANCH_3 = BankBranch.builder()
            .address("789 street")
            .bankName("Test Bank UK")
            .countryISO2("UK")
            .countryName("UNITED KINGDOM")
            .swiftCode("TESTUS55")
            .build();

    private final static BankBranch BANK_BRANCH_4 = BankBranch.builder()
            .address("Sloneczna 15")
            .bankName("Test Bank PL")
            .countryISO2("PL")
            .countryName("POLAND")
            .swiftCode("TESTPL44")
            .build();

    private final static BankHq BANK_HQ_1 = BankHq.builder()
            .address("123 Main St")
            .bankName("Test Bank US")
            .countryISO2("US")
            .countryName("UNITED STATES")
            .swiftCode("TESTUSBRXXX")
            .bankBranches(List.of(BANK_BRANCH_1, BANK_BRANCH_2))
            .build();

    private final static BankHq BANK_HQ_2 = BankHq.builder()
            .address("Test Street")
            .bankName("Test Bank UK")
            .countryISO2("UK")
            .countryName("UNITED KINGDOM")
            .swiftCode("TESTUS55XXX")
            .bankBranches(List.of(BANK_BRANCH_3))
            .build();

    {
        BANK_BRANCH_1.setBankHq(BANK_HQ_1);
        BANK_BRANCH_2.setBankHq(BANK_HQ_1);
        BANK_BRANCH_3.setBankHq(BANK_HQ_2);
    }

    private final static List<BankBranch> BANK_BRANCHES = List.of(BANK_BRANCH_1, BANK_BRANCH_2, BANK_BRANCH_3, BANK_BRANCH_4);

    private final static List<BankHq> BANK_HQS = List.of(BANK_HQ_1, BANK_HQ_2);

    public static List<BankBranch> getBankBranches() {
        return BANK_BRANCHES.stream()
                .map(bankBranch -> bankBranch.toBuilder().build())
                .toList();
    }

    public static List<BankHq> getBankHqs() {
        return BANK_HQS.stream()
                .map(bankHq -> bankHq.toBuilder().build())
                .toList();
    }
}
