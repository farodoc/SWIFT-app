package com.swift.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.swift.dto.SwiftCodeEntryRequest;

import java.io.IOException;

public class SwiftCodeEntryRequestDeserializer extends JsonDeserializer<SwiftCodeEntryRequest> {

    @Override
    public SwiftCodeEntryRequest deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        String address = getTrimmedUpperCaseValue(node, "address");
        String bankName = getTrimmedUpperCaseValue(node, "bankName");
        String countryISO2 = getTrimmedUpperCaseValue(node, "countryISO2");
        String countryName = getTrimmedUpperCaseValue(node, "countryName");
        Boolean isHeadquarter = node.get("isHeadquarter") != null && node.get("isHeadquarter").asBoolean();
        String swiftCode = getTrimmedUpperCaseValue(node, "swiftCode");

        return SwiftCodeEntryRequest.builder()
                .address(address)
                .bankName(bankName)
                .countryISO2(countryISO2)
                .countryName(countryName)
                .isHeadquarter(isHeadquarter)
                .swiftCode(swiftCode)
                .build();
    }

    private String getTrimmedUpperCaseValue(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return fieldNode != null ? fieldNode.asText().toUpperCase().trim() : null;
    }

}
