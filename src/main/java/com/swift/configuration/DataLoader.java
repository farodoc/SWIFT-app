package com.swift.configuration;

import com.swift.model.BankBranch;
import com.swift.model.BankHq;
import com.swift.repository.BankBranchRepository;
import com.swift.repository.BankHqRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Objects;

@Configuration
public class DataLoader {

    private final BankHqRepository bankHqRepository;
    private final BankBranchRepository bankBranchRepository;
    private final static String BANK_HQ_SUFFIX = "XXX";
    private final static int BANK_SWIFT_CODE_PREFIX_LENGTH = 8;

    public DataLoader(BankHqRepository bankHqRepository, BankBranchRepository bankBranchRepository) {
        this.bankHqRepository = bankHqRepository;
        this.bankBranchRepository = bankBranchRepository;
    }

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            try (InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/data.xlsx"))) {
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);

                loadHeadquarters(sheet);
                loadBranches(sheet);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load data from Excel file", e);
            }
        };
    }

    private void loadHeadquarters(Sheet sheet) {
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            String swiftCode = row.getCell(1).getStringCellValue();
            if (swiftCode.endsWith(BANK_HQ_SUFFIX)) {
                String bankName = row.getCell(3).getStringCellValue();
                String address = row.getCell(4).getStringCellValue();
                String countryISO2 = row.getCell(0).getStringCellValue();
                String countryName = row.getCell(6).getStringCellValue();

                BankHq bankHq = BankHq.builder()
                        .swiftCode(swiftCode)
                        .bankName(bankName)
                        .address(address)
                        .countryISO2(countryISO2)
                        .countryName(countryName)
                        .build();

                bankHqRepository.save(bankHq);
            }
        }
    }

    private void loadBranches(Sheet sheet) {
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            String swiftCode = row.getCell(1).getStringCellValue();
            if (!swiftCode.endsWith(BANK_HQ_SUFFIX)) {
                String bankName = row.getCell(3).getStringCellValue();
                String address = row.getCell(4).getStringCellValue();
                String countryISO2 = row.getCell(0).getStringCellValue();
                String countryName = row.getCell(6).getStringCellValue();

                String hqSwiftCode = swiftCode.substring(0, BANK_SWIFT_CODE_PREFIX_LENGTH) + BANK_HQ_SUFFIX;
                BankHq bankHq = bankHqRepository.findById(hqSwiftCode).orElse(null);

                BankBranch bankBranch = BankBranch.builder()
                        .swiftCode(swiftCode)
                        .bankName(bankName)
                        .address(address)
                        .countryISO2(countryISO2)
                        .countryName(countryName)
                        .bankHq(bankHq)
                        .build();

                bankBranchRepository.save(bankBranch);
            }
        }
    }
}