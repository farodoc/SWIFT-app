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

        String address = getUpperCaseValue(node, "address");
        String bankName = getUpperCaseValue(node, "bankName");
        String countryISO2 = getUpperCaseValue(node, "countryISO2");
        String countryName = getUpperCaseValue(node, "countryName");
        Boolean isHeadquarter = node.get("isHeadquarter").asBoolean();
        String swiftCode = getUpperCaseValue(node, "swiftCode");

        return SwiftCodeEntryRequest.builder()
                .address(address)
                .bankName(bankName)
                .countryISO2(countryISO2)
                .countryName(countryName)
                .isHeadquarter(isHeadquarter)
                .swiftCode(swiftCode)
                .build();
    }

    private String getUpperCaseValue(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return fieldNode != null ? fieldNode.asText().toUpperCase().trim() : null;
    }

}
