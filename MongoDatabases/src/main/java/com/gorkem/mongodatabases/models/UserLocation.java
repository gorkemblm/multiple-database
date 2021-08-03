package com.gorkem.mongodatabases.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user-location")
public class UserLocation {

    @Id
    private String identityNumber;
    private String databaseAddress;
}
