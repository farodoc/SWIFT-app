package com.swift.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@Table(name = "bank_branches")
@AllArgsConstructor
@NoArgsConstructor
public class BankBranch {

    @Id
    @Column(name = "swift_code")
    private String swiftCode;

    @Column(name = "code_type")
    private String codeType;

    @ManyToOne(fetch = FetchType.LAZY)
    private BankHq bankHq;

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

}
