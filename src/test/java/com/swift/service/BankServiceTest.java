package com.swift.service;

import com.swift.TestConst;
import com.swift.dto.BankBranchDto;
import com.swift.dto.BankHqDto;
import com.swift.dto.CountrySwiftCodesResponse;
import com.swift.dto.HqsBranchBank;
import com.swift.model.BankBranch;
import com.swift.model.BankHq;
import com.swift.repository.BankBranchRepository;
import com.swift.repository.BankHqRepository;
import com.swift.util.BankMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BankServiceTest {

    @Mock
    private BankHqRepository bankHqRepository;

    @Mock
    private BankBranchRepository bankBranchRepository;

    @Mock
    private BankMapper bankMapper;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private BankService bankService;

    @Test
    public void testGetBank_headquarterSwiftCode() {
        // Given
        String swiftCode = "TESTUSHQXXX";
        Optional<BankHq> bankHq = Optional.of(TestConst.getBankHqs().getFirst());
        Optional<BankHqDto> bankHqDto = Optional.of(BankHqDto.builder().swiftCode(swiftCode).build());

        when(bankHqRepository.findBySwiftCode(swiftCode)).thenReturn(bankHq);
        when(bankMapper.convertHqToDto(any())).thenReturn(bankHqDto);

        // When
        Optional<?> result = bankService.getBank(swiftCode);

        // Then
        assertTrue(result.isPresent());
        assertInstanceOf(BankHqDto.class, result.get());
        assertEquals(swiftCode, ((BankHqDto) result.get()).getSwiftCode());
    }

    @Test
    public void testGetBank_branchSwiftCode() {
        // Given
        String swiftCode = "TESTUSBR001";
        Optional<BankBranch> bankBranch = Optional.of(TestConst.getBankBranches().getFirst());
        Optional<BankBranchDto> bankBranchDto = Optional.of(BankBranchDto.builder().swiftCode(swiftCode).build());

        when(bankBranchRepository.findBySwiftCode(swiftCode)).thenReturn(bankBranch);
        when(bankMapper.convertBranchToDto(any())).thenReturn(bankBranchDto);

        // When
        Optional<?> result = bankService.getBank(swiftCode);

        // Then
        assertTrue(result.isPresent());
        assertInstanceOf(BankBranchDto.class, result.get());
        assertEquals(swiftCode, ((BankBranchDto) result.get()).getSwiftCode());
    }

    @Test
    public void testGetBanksByCountry_BanksFound() {
        // Given
        String countryISO2 = "US";
        BankHq bankHq = TestConst.getBankHqs().getFirst();
        BankBranch bankBranch = TestConst.getBankBranches().getFirst();

        when(bankBranchRepository.findByCountryISO2(countryISO2)).thenReturn(List.of(bankBranch));
        when(bankHqRepository.findByCountryISO2(countryISO2)).thenReturn(List.of(bankHq));
        when(bankMapper.convertToHqsBranchBank((BankBranch) any())).thenReturn(HqsBranchBank.builder().build());
        when(bankMapper.convertToHqsBranchBank((BankHq) any())).thenReturn(HqsBranchBank.builder().build());

        // When
        CountrySwiftCodesResponse result = bankService.getBanksByCountry(countryISO2);

        // Then
        assertNotNull(result);
        assertEquals(countryISO2, result.getCountryISO2());
        assertNotNull(result.getCountryName());
        assertEquals(result.getCountryName(), "UNITED STATES");
        assertFalse(result.getSwiftCodes().isEmpty());
    }

    @Test
    public void testGetBanksByCountry_BanksNotFound() {
        // Given
        String countryISO2 = "US";

        when(bankBranchRepository.findByCountryISO2(countryISO2)).thenReturn(List.of());
        when(bankHqRepository.findByCountryISO2(countryISO2)).thenReturn(List.of());

        // When
        CountrySwiftCodesResponse result = bankService.getBanksByCountry(countryISO2);

        // Then
        assertNotNull(result);
        assertEquals(countryISO2, result.getCountryISO2());
        assertNull(result.getCountryName());
        assertTrue(result.getSwiftCodes().isEmpty());
    }
}
