package com.swift.repository;

import com.swift.model.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch, String> {

    Optional<BankBranch> findBySwiftCode(String swiftCode);

    List<BankBranch> findByCountryISO2(String countryISO2);

    List<BankBranch> findBySwiftCodeStartingWith(String swiftCodePrefix);

    void deleteBySwiftCode(String swiftCode);

}
