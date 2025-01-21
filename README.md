# SWIFT codes app

### Introduction
This is a simple app that allows you to search for SWIFT codes of banks. 
It uses the prefetched data stored in a `data.xlsx` file.

Codes ending with “XXX” represent a bank's headquarters, otherwise branch.
Branch codes are associated with a headquarters if their first 8 characters match.

### Endpoints
- `GET:` `/v1/swift-codes/{swift-code}` - retrieves details of a single SWIFT code whether for a headquarters or branches.

###### Response structure for headquarter swift code:
```json
{   
  "address": "string", 
  "bankName": "string", 
  "countryISO2": "string", 
  "countryName": "string", 
  "isHeadquarter": "bool", 
  "swiftCode": "string",
  "branches": [ 
    { 
      "address": "string", 
      "bankName": "string", 
      "countryISO2": "string", 
      "isHeadquarter": "bool", 
      "swiftCode": "string" 
    }, 
    { 
      "address": "string", 
      "bankName": "string", 
      "countryISO2": "string", 
      "isHeadquarter": "bool", 
      "swiftCode": "string" 
    }, ...
  ] 
}
```

###### Response structure for branch swift code:
```json
{
  "address": "string", 
  "bankName": "string", 
  "countryISO2": "string", 
  "countryName": "string", 
  "isHeadquarter": "bool", 
  "swiftCode": "string"
}
```

- `GET:` `/v1/swift-codes/country/{countryISO2code}` - returns all SWIFT codes with details for a specific country (both headquarters and branches).

###### Response structure:
```json
{ 
  "countryISO2": "string", 
  "countryName": "string", 
  "swiftCodes": [ 
    { 
      "address": "string",
      "bankName": "string", 
      "countryISO2": "string",
      "isHeadquarter": "bool",
      "swiftCode": "string"
    },
    { 
      "address": "string", 
      "bankName": "string", 
      "countryISO2": "string", 
      "isHeadquarter": "bool", 
      "swiftCode": "string"
    }, ... 
  ]
}
```

- `POST:` `/v1/swift-codes` - adds new SWIFT code entries to the database for a specific country.

###### Request structure:
```json
{ 
  "address": "string",
  "bankName": "string", 
  "countryISO2": "string", 
  "countryName": "string", 
  "isHeadquarter": "bool", 
  "swiftCode": "string"
}
```

###### Response structure:
```json
{
  "message": "string"
}
```

- `DELETE:` `/v1/swift-codes/{swift-code}` - deletes swift-code data if swiftCode, bankName and countryISO2 matches the one in the database.

###### Response structure:
```json
{
  "message": "string"
}
```

More in-depth endpoint documentation can be found in swagger-ui at `/swagger-ui/index.html#/`.