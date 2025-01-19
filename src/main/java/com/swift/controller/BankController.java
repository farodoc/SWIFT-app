package com.swift.controller;

import com.swift.dto.CountrySwiftCodesResponse;
import com.swift.dto.SwiftCodeEntryRequest;
import com.swift.service.BankService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(bankService.getBanksByCountry(countryISO2.toUpperCase()));
    }

    @PostMapping
    public ResponseEntity<?> createBank(@RequestBody @Valid SwiftCodeEntryRequest swiftCodeEntryRequest) {
        bankService.createBank(swiftCodeEntryRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<?> deleteBank(@PathVariable String swiftCode) {
        bankService.deleteBank(swiftCode);
        return ResponseEntity.ok("Entry deleted");
    }

}
