package com.swift.repository;

import com.swift.model.BankBranch;
import com.swift.model.BankHq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankHqRepository extends JpaRepository<BankHq, String> {

    Optional<BankHq> findBySwiftCode(String swiftCode);

    List<BankHq> findByCountryISO2(String countryISO2);

    void deleteBySwiftCode(String swiftCode);

}
