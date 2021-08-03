package com.gorkem.demoPLPostgres.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDto {

    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String identityNumber;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
}
