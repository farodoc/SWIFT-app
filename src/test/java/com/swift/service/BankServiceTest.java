package com.swift.service;

import com.swift.TestConst;
import com.swift.dto.*;
import com.swift.model.BankBranch;
import com.swift.model.BankHq;
import com.swift.repository.BankBranchRepository;
import com.swift.repository.BankHqRepository;
import com.swift.util.BankMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Autowired
    BankHqRepository integrationBankHqRepository;
    @Autowired
    BankBranchRepository integrationBankBranchRepository;
    @Autowired
    private BankService integrationBankService;
    @Autowired
    private BankMapper integrationBankMapper;

    @Autowired
    private EntityManager integrationEntityManager;

    @BeforeEach
    public void setup() {
        integrationBankService = new BankService(integrationBankHqRepository, integrationBankBranchRepository, integrationBankMapper, integrationEntityManager);
        integrationBankBranchRepository.saveAll(TestConst.getBankBranches());
        integrationBankHqRepository.saveAll(TestConst.getBankHqs());
    }

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
    public void testGetBank_branchSwiftCode_Integration() {
        // Given
        String swiftCode = "TESTUSBR001";

        // When
        Optional<?> result = integrationBankService.getBank(swiftCode);

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
        assertEquals("UNITED STATES", result.getCountryName());
        assertFalse(result.getSwiftCodes().isEmpty());
    }

    @Test
    public void testGetBanksByCountry_BanksFound_Integration() {
        // Given
        String countryISO2 = "US";

        // When
        CountrySwiftCodesResponse result = integrationBankService.getBanksByCountry(countryISO2);

        // Then
        assertNotNull(result);
        assertEquals(countryISO2, result.getCountryISO2());
        assertNotNull(result.getCountryName());
        assertEquals("UNITED STATES", result.getCountryName());
        assertEquals(3, result.getSwiftCodes().size());
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

    @Test
    public void testGetBanksByCountry_BanksNotFound_Integration() {
        // Given
        String countryISO2 = "KK";

        // When
        CountrySwiftCodesResponse result = integrationBankService.getBanksByCountry(countryISO2);

        // Then
        assertNotNull(result);
        assertEquals(countryISO2, result.getCountryISO2());
        assertNull(result.getCountryName());
        assertTrue(result.getSwiftCodes().isEmpty());
    }

    @Test
    public void testCreateBank_BankHq_ExpectSuccess() {
        // Given
        String swiftCode = "TESTUSHQXXX";
        BankHq bankHq = TestConst.getBankHqs().getFirst();
        SwiftCodeEntryRequest swiftCodeEntryRequest = SwiftCodeEntryRequest.builder()
                .swiftCode(swiftCode)
                .isHeadquarter(true)
                .build();

        when(bankHqRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.empty());
        when(bankMapper.convertToBankHq(swiftCodeEntryRequest)).thenReturn(bankHq);
        when(bankBranchRepository.findBySwiftCodeStartingWith(anyString())).thenReturn(List.of());
        when(bankHqRepository.save(bankHq)).thenReturn(bankHq);

        // When
        bankService.createBank(swiftCodeEntryRequest);

        // Then
        // No exception thrown
    }

    @Test
    public void testCreateBank_BankHq_ExpectSuccess_Integration() {
        // Given
        BankHq bankHq = TestConst.getBankHqs().getFirst();
        SwiftCodeEntryRequest swiftCodeEntryRequest = SwiftCodeEntryRequest.builder()
                .address(bankHq.getAddress())
                .bankName(bankHq.getBankName())
                .countryISO2(bankHq.getCountryISO2())
                .countryName(bankHq.getCountryName())
                .isHeadquarter(true)
                .swiftCode("AAAAAAAAXXX")
                .build();

        // When
        integrationBankService.createBank(swiftCodeEntryRequest);

        // Then
        // No exception thrown
    }

    @Test
    public void testCreateBank_BankBranch_ExpectSuccess() {
        // Given
        String swiftCode = "TESTUSHQAAA";
        BankBranch bankBranch = TestConst.getBankBranches().getFirst();
        SwiftCodeEntryRequest swiftCodeEntryRequest = SwiftCodeEntryRequest.builder()
                .swiftCode(swiftCode)
                .isHeadquarter(false)
                .build();

        when(bankBranchRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.empty());
        when(bankMapper.convertToBankBranch(swiftCodeEntryRequest)).thenReturn(bankBranch);
        when(bankHqRepository.findBySwiftCode(anyString())).thenReturn(Optional.empty());
        when(bankBranchRepository.save(bankBranch)).thenReturn(bankBranch);

        // When
        bankService.createBank(swiftCodeEntryRequest);

        // Then
        // No exception thrown
    }

    @Test
    public void testCreateBank_BankBranch_ExpectSuccess_Integration() {
        // Given
        BankBranch bankBranch = TestConst.getBankBranches().getFirst();
        SwiftCodeEntryRequest swiftCodeEntryRequest = SwiftCodeEntryRequest.builder()
                .address(bankBranch.getAddress())
                .bankName(bankBranch.getBankName())
                .countryISO2(bankBranch.getCountryISO2())
                .countryName(bankBranch.getCountryName())
                .isHeadquarter(false)
                .swiftCode("BBBBBBBB")
                .build();

        // When
        integrationBankService.createBank(swiftCodeEntryRequest);

        // Then
        // No exception thrown
    }

    @Test
    public void testCreateBank_BankBranchWithAlreadyExistingSwiftCode_ExpectIllegalArgumentException() {
        // Given
        String swiftCode = "TESTUSBR001";
        BankBranch bankBranch = TestConst.getBankBranches().getFirst();
        SwiftCodeEntryRequest swiftCodeEntryRequest = SwiftCodeEntryRequest.builder()
                .swiftCode(swiftCode)
                .isHeadquarter(false)
                .build();

        when(bankBranchRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.of(bankBranch));

        // Then
        assertThrows(IllegalArgumentException.class, () -> bankService.createBank(swiftCodeEntryRequest));
    }

    @Test
    public void testCreateBank_BankBranchWithAlreadyExistingSwiftCode_ExpectIllegalArgumentException_Integration() {
        // Given
        String swiftCode = "TESTUSBR001";
        BankBranch bankBranch = TestConst.getBankBranches().getFirst();
        SwiftCodeEntryRequest swiftCodeEntryRequest = SwiftCodeEntryRequest.builder()
                .address(bankBranch.getAddress())
                .bankName(bankBranch.getBankName())
                .countryISO2(bankBranch.getCountryISO2())
                .countryName(bankBranch.getCountryName())
                .isHeadquarter(false)
                .swiftCode(swiftCode)
                .build();


        // Then
        assertThrows(IllegalArgumentException.class, () -> integrationBankService.createBank(swiftCodeEntryRequest));
    }

    @Test
    @Transactional
    public void testDeleteBank_BankHq_ExpectSuccess_Integration() {
        // Given
        BankHq bankHq = TestConst.getBankHqs().getFirst();
        String swiftCode = bankHq.getSwiftCode();

        // When
        integrationBankService.deleteBank(swiftCode);

        // Then
        assertTrue(integrationBankHqRepository.findBySwiftCode(swiftCode).isEmpty());
    }
}
