package com.swift.service;

import com.swift.TestConst;
import com.swift.dto.BankBranchDto;
import com.swift.dto.BankHqDto;
import com.swift.model.BankHq;
import com.swift.repository.BankBranchRepository;
import com.swift.repository.BankHqRepository;
import com.swift.util.BankMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

}
