package com.swift.controller;

import com.swift.dto.CountrySwiftCodesResponse;
import com.swift.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/swift-codes")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/{swiftCode}")
    public ResponseEntity<?> getBank(@PathVariable String swiftCode) {
        Optional<?> bank = bankService.getBank(swiftCode);
        return bank.isPresent() ? ResponseEntity.ok(bank.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/country/{countryISO2}")
    public ResponseEntity<CountrySwiftCodesResponse> getBanksByCountry(@PathVariable String countryISO2) {
        return ResponseEntity.ok(bankService.getBanksByCountry(countryISO2));
    }

}
