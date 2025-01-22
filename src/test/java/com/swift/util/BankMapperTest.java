package com.swift.util;

import com.swift.TestConst;
import com.swift.dto.BankBranchDto;
import com.swift.dto.BankHqDto;
import com.swift.dto.HqsBranchBank;
import com.swift.dto.SwiftCodeEntryRequest;
import com.swift.model.BankBranch;
import com.swift.model.BankHq;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BankMapperTest {

    private final BankMapper bankMapper = new BankMapper();

    @Test
    void testConvertBranchToDto() {
        // Given
        BankBranch bankBranch = TestConst.getBankBranches().getFirst();

        // When
        Optional<BankBranchDto> result = bankMapper.convertBranchToDto(Optional.of(bankBranch));

        // Then
        assertTrue(result.isPresent());
        BankBranchDto dto = result.get();
        assertEquals("456 Main St", dto.getAddress());
        assertEquals("Test Bank US", dto.getBankName());
        assertEquals("US", dto.getCountryISO2());
        assertEquals("UNITED STATES", dto.getCountryName());
        assertFalse(dto.getIsHeadquarter());
        assertEquals("TESTUSBR001", dto.getSwiftCode());
    }

    @Test
    void testConvertBranchToDto_EmptyBankBranch() {
        // When
        Optional<BankBranchDto> result = bankMapper.convertBranchToDto(Optional.empty());

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void testConvertHqToDto() {
        // Given
        BankHq bankHq = TestConst.getBankHqs().getFirst();

        // When
        Optional<BankHqDto> result = bankMapper.convertHqToDto(Optional.of(bankHq));
        HqsBranchBank bankBranchDto1 = bankMapper.convertToHqsBranchBank(TestConst.getBankBranches().getFirst());
        HqsBranchBank bankBranchDto2 = bankMapper.convertToHqsBranchBank(TestConst.getBankBranches().get(1));

        // Then
        assertTrue(result.isPresent());
        BankHqDto dto = result.get();
        assertEquals("123 Main St", dto.getAddress());
        assertEquals("Test Bank US", dto.getBankName());
        assertEquals("US", dto.getCountryISO2());
        assertEquals("UNITED STATES", dto.getCountryName());
        assertTrue(dto.getIsHeadquarter());
        assertEquals("TESTUSBRXXX", dto.getSwiftCode());
        assertEquals(2, dto.getBranches().size());
        assertEquals(bankBranchDto1, dto.getBranches().getFirst());
        assertEquals(bankBranchDto2, dto.getBranches().get(1));
    }

    @Test
    void testConvertHqToDto_EmptyBankHq() {
        // When
        Optional<BankHqDto> result = bankMapper.convertHqToDto(Optional.empty());

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void testConvertToHqsBranchBankFromBranch() {
        // Given
        BankBranch bankBranch = TestConst.getBankBranches().get(3);

        // When
        HqsBranchBank result = bankMapper.convertToHqsBranchBank(bankBranch);

        // Then
        assertEquals("Sloneczna 15", result.getAddress());
        assertEquals("Test Bank PL", result.getBankName());
        assertEquals("PL", result.getCountryISO2());
        assertFalse(result.getIsHeadquarter());
        assertEquals("TESTPL44", result.getSwiftCode());
    }

    @Test
    void testConvertToHqsBranchBankFromHq() {
        // Given
        BankHq bankHq = TestConst.getBankHqs().get(1);

        // When
        HqsBranchBank result = bankMapper.convertToHqsBranchBank(bankHq);

        // Then
        assertEquals("Test Street", result.getAddress());
        assertEquals("Test Bank UK", result.getBankName());
        assertEquals("UK", result.getCountryISO2());
        assertTrue(result.getIsHeadquarter());
        assertEquals("TESTUS55XXX", result.getSwiftCode());
    }

    @Test
    void testConvertToBankHq() {
        // Given
        SwiftCodeEntryRequest request = SwiftCodeEntryRequest.builder()
                .address("123 Main St")
                .bankName("Test Bank")
                .countryISO2("US")
                .countryName("United States")
                .swiftCode("TESTUS33XXX")
                .build();

        // When
        BankHq result = bankMapper.convertToBankHq(request);

        // Then
        assertEquals("123 Main St", result.getAddress());
        assertEquals("Test Bank", result.getBankName());
        assertEquals("US", result.getCountryISO2());
        assertEquals("United States", result.getCountryName());
        assertEquals("TESTUS33XXX", result.getSwiftCode());
    }

    @Test
    void testConvertToBankBranch() {
        // Given
        SwiftCodeEntryRequest request = SwiftCodeEntryRequest.builder()
                .address("123 Main St")
                .bankName("Test Bank")
                .countryISO2("US")
                .countryName("United States")
                .swiftCode("TESTUS33")
                .build();

        // When
        BankBranch result = bankMapper.convertToBankBranch(request);

        // Then
        assertEquals("123 Main St", result.getAddress());
        assertEquals("Test Bank", result.getBankName());
        assertEquals("US", result.getCountryISO2());
        assertEquals("United States", result.getCountryName());
        assertEquals("TESTUS33", result.getSwiftCode());
    }
}
