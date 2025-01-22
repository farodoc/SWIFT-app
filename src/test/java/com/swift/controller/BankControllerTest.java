package com.swift.controller;

import com.swift.TestConst;
import com.swift.dto.*;
import com.swift.repository.BankBranchRepository;
import com.swift.repository.BankHqRepository;
import com.swift.service.BankService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BankControllerTest {
    @Mock
    BankService bankService;

    @InjectMocks
    BankController controllerUnderTest;

    @Autowired
    BankService service;

    @Autowired
    BankHqRepository bankHqRepository;

    @Autowired
    BankBranchRepository bankBranchRepository;

    private MockMvc mockMvc;

    private MockMvc integrationMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
        this.integrationMvc = MockMvcBuilders.standaloneSetup(new BankController(service)).build();
        bankHqRepository.saveAll(TestConst.getBankHqs());
        bankBranchRepository.saveAll(TestConst.getBankBranches());
    }

    @Test
    public void testGetBank_SampleBankHq_ExpectedCorrectValuesInResponseBody() throws Exception {
        //given
        String bankHqSwiftCode = TestConst.getBankHqs().getFirst().getSwiftCode();
        BankHqDto bankHqDto = BankHqDto.builder().swiftCode(bankHqSwiftCode).build();

        //when
        doReturn(Optional.of(bankHqDto)).when(bankService).getBank(bankHqSwiftCode);

        //then
        mockMvc.perform(get("/v1/swift-codes/" + bankHqSwiftCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.swiftCode").value(bankHqSwiftCode));
    }

    @Test
    @Transactional
    public void testGetBank_SampleBankHq_ExpectedCorrectValuesInResponseBody_Integration() throws Exception {
        //given
        String bankHqSwiftCode = TestConst.getBankHqs().getFirst().getSwiftCode();

        //then
        integrationMvc.perform(get("/v1/swift-codes/" + bankHqSwiftCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.swiftCode").value(bankHqSwiftCode));
    }

    @Test
    public void testGetBank_SampleBankBranch_ExpectedCorrectValuesInResponseBody() throws Exception {
        //given
        String bankBranchSwiftCode = TestConst.getBankBranches().getFirst().getSwiftCode();
        BankBranchDto bankBranchDto = BankBranchDto.builder().swiftCode(bankBranchSwiftCode).build();

        //when
        doReturn(Optional.of(bankBranchDto)).when(bankService).getBank(bankBranchSwiftCode);

        //then
        mockMvc.perform(get("/v1/swift-codes/" + bankBranchSwiftCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.swiftCode").value(bankBranchSwiftCode));
    }

    @Test
    public void testGetBank_BankNotFound_ExpectedStatusNotFound() throws Exception {
        //given
        String swiftCode = "AAAAAAAA";

        //when
        doReturn(Optional.empty()).when(bankService).getBank(swiftCode);

        //then
        mockMvc.perform(get("/v1/swift-codes/" + swiftCode))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetBank_BankNotFound_ExpectedStatusNotFound_Integration() throws Exception {
        //given
        String swiftCode = "XXXXXXXX";

        //then
        integrationMvc.perform(get("/v1/swift-codes/" + swiftCode))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBanksByCountry_SampleListOfBanks_ExpectedCorrectValuesInResponseBody() throws Exception {
        //given
        String countryISO2 = "UK";
        List<HqsBranchBank> swiftCodes = List.of(
                HqsBranchBank.builder().swiftCode("TESTUS55").build(),
                HqsBranchBank.builder().swiftCode("TESTUKHQXXX").build()
        );
        CountrySwiftCodesResponse response = CountrySwiftCodesResponse.builder()
                .countryISO2(countryISO2)
                .countryName("UNITED KINGDOM")
                .swiftCodes(swiftCodes)
                .build();

        //when
        when(bankService.getBanksByCountry(anyString())).thenReturn(response);

        //then
        mockMvc.perform(get("/v1/swift-codes/country/" + countryISO2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryISO2").value(countryISO2))
                .andExpect(jsonPath("$.countryName").value("UNITED KINGDOM"))
                .andExpect(jsonPath("$.swiftCodes").isArray())
                .andExpect(jsonPath("$.swiftCodes").isNotEmpty())
                .andExpect(jsonPath("$.swiftCodes[0].swiftCode").value("TESTUS55"))
                .andExpect(jsonPath("$.swiftCodes[1].swiftCode").value("TESTUKHQXXX"));
    }

    @Test
    @Transactional
    public void getBanksByCountry_SampleListOfBanks_ExpectedCorrectValuesInResponseBody_Integration() throws Exception {
        //given
        String countryISO2 = "UK";

        //then
        integrationMvc.perform(get("/v1/swift-codes/country/" + countryISO2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryISO2").value(countryISO2))
                .andExpect(jsonPath("$.countryName").value("UNITED KINGDOM"))
                .andExpect(jsonPath("$.swiftCodes").isArray())
                .andExpect(jsonPath("$.swiftCodes").isNotEmpty())
                .andExpect(jsonPath("$.swiftCodes[0].swiftCode").value("TESTUS55"))
                .andExpect(jsonPath("$.swiftCodes[1].swiftCode").value("TESTUS55XXX"));
    }

    @Test
    public void getBanksByCountry_CountryNotFound_ExpectedStatusNotFound() throws Exception {
        //given
        String countryISO2 = "AA";
        CountrySwiftCodesResponse response = CountrySwiftCodesResponse.builder()
                .countryISO2(countryISO2)
                .swiftCodes(List.of())
                .build();

        //when
        when(bankService.getBanksByCountry(anyString())).thenReturn(response);

        //then
        mockMvc.perform(get("/v1/swift-codes/country/" + countryISO2))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBanksByCountry_CountryNotFound_ExpectedStatusNotFound_Integration() throws Exception {
        //given
        String countryISO2 = "AA";

        //then
        integrationMvc.perform(get("/v1/swift-codes/country/" + countryISO2))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBanksByCountry_BadISO2Code_ExpectedStatusBadRequest() throws Exception {
        //given
        String countryISO2 = "AAA";

        //then
        mockMvc.perform(get("/v1/swift-codes/country/" + countryISO2))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getBanksByCountry_BadISO2Code_ExpectedStatusBadRequest_Integration() throws Exception {
        //given
        String countryISO2 = "AAA";

        //then
        integrationMvc.perform(get("/v1/swift-codes/country/" + countryISO2))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBank_SampleBankBranchRequest_ExpectedStatusOk() throws Exception {
        //given
        String request = """
                {
                  "address": "Sloneczna 15",
                  "bankName": "Test Bank PL",
                  "countryISO2": "PL",
                  "countryName": "POLAND",
                  "isHeadquarter": false,
                  "swiftCode": "AAAAAAAA"
                }
                """;

        //when
        doNothing().when(bankService).createBank(any(SwiftCodeEntryRequest.class));

        //then
        mockMvc.perform(post("/v1/swift-codes")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    public void createBank_SampleBankBranchRequest_ExpectedStatusOk_Integration() throws Exception {
        //given
        String request = """
                {
                  "address": "Sloneczna 15",
                  "bankName": "Test Bank PL",
                  "countryISO2": "PL",
                  "countryName": "POLAND",
                  "isHeadquarter": false,
                  "swiftCode": "AAAAAAAA"
                }
                """;

        //then
        integrationMvc.perform(post("/v1/swift-codes")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isOk());

        assertTrue(bankBranchRepository.findBySwiftCode("AAAAAAAA").isPresent());
    }

    @Test
    public void createBank_BadAddressInRequest_ExpectedStatusBadRequest() throws Exception {
        //given
        String request = """
                {
                  "address": "!?@#$%^&*()",
                  "bankName": "Test Bank PL",
                  "countryISO2": "PL",
                  "countryName": "POLAND",
                  "isHeadquarter": false,
                  "swiftCode": "AAAAAAAA"
                }
                """;

        //then
        mockMvc.perform(post("/v1/swift-codes")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBank_BadBankNameInRequest_ExpectedStatusBadRequest() throws Exception {
        //given
        String request = """
                {
                  "address": "Sloneczna 15",
                  "bankName": "!?@#$%^&*()",
                  "countryISO2": "PL",
                  "countryName": "POLAND",
                  "isHeadquarter": false,
                  "swiftCode": "AAAAAAAA"
                }
                """;

        //then
        mockMvc.perform(post("/v1/swift-codes")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBank_BadCountryISO2CodeInRequest_ExpectedStatusBadRequest() throws Exception {
        //given
        String request = """
                {
                  "address": "Sloneczna 15",
                  "bankName": "Test Bank PL",
                  "countryISO2": "!?@#$%^&*()",
                  "countryName": "POLAND",
                  "isHeadquarter": false,
                  "swiftCode": "AAAAAAAA"
                }
                """;

        //then
        mockMvc.perform(post("/v1/swift-codes")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBank_BadCountryNameInRequest_ExpectedStatusBadRequest() throws Exception {
        //given
        String request = """
                {
                  "address": "Sloneczna 15",
                  "bankName": "Test Bank PL",
                  "countryISO2": "PL",
                  "countryName": "!?@#$%^&*()",
                  "isHeadquarter": false,
                  "swiftCode": "AAAAAAAA"
                }
                """;

        //then
        mockMvc.perform(post("/v1/swift-codes")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBank_BadSwiftCodeInRequest_ExpectedStatusBadRequest() throws Exception {
        //given
        String request = """
                {
                  "address": "Sloneczna 15",
                  "bankName": "Test Bank PL",
                  "countryISO2": "PL",
                  "countryName": "POLAND",
                  "isHeadquarter": false,
                  "swiftCode": "AA"
                }
                """;

        //then
        mockMvc.perform(post("/v1/swift-codes")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBank_BadSwiftCodeForHeadquarterState_ExpectedStatusBadRequest() throws Exception {
        //given
        String request = """
                {
                  "address": "Sloneczna 15",
                  "bankName": "Test Bank PL",
                  "countryISO2": "PL",
                  "countryName": "POLAND",
                  "isHeadquarter": true,
                  "swiftCode": "AAAAAAAA"
                }
                """;

        //then
        mockMvc.perform(post("/v1/swift-codes")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBank_ShortRequest_ExpectedStatusBadRequest() throws Exception {
        //given
        String request = """
                {
                  "address": "Sloneczna 15",
                  "countryISO2": "PL"
                }
                """;

        //then
        mockMvc.perform(post("/v1/swift-codes")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBank_ExpectedStatusOk() throws Exception {
        //given
        String swiftCode = "AAAAAAAA";

        //when
        doNothing().when(bankService).deleteBank(swiftCode);

        //then
        mockMvc.perform(delete("/v1/swift-codes/" + swiftCode))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBank_ExpectedStatusOk_Integration() throws Exception {
        //given
        String swiftCode = "TESTPL44";

        //then
        assertTrue(bankBranchRepository.findBySwiftCode(swiftCode).isPresent());

        integrationMvc.perform(delete("/v1/swift-codes/" + swiftCode))
                .andExpect(status().isOk());

        assertTrue(bankBranchRepository.findBySwiftCode(swiftCode).isEmpty());
    }
}
