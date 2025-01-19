package com.swift.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@Table(name = "bank_headquarters")
@AllArgsConstructor
@NoArgsConstructor
public class BankHq {

    @Id
    @Column(name = "swift_code")
    private String swiftCode;

    @Column(name = "code_type")
    private String codeType;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "address")
    private String address;

    @Column(name = "country_iso2")
    private String countryISO2;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "town_name")
    private String townName;

    @Column(name = "time_zone")
    private String timeZone;

    @OneToMany(
            mappedBy = "bankHq",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BankBranch> bankBranches = new ArrayList<>();
}
