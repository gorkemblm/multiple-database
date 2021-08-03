package com.gorkem.mongodatabases.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLocationGetDto {

    private String identityNumber;
    private String databaseAddress;
}
