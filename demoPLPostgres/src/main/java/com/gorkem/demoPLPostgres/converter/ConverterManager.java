package com.gorkem.demoPLPostgres.converter;

import com.gorkem.demoPLPostgres.entity.User;
import com.gorkem.demoPLPostgres.entity.dtos.UserGetDto;
import com.gorkem.demoPLPostgres.entity.dtos.UserRegisterDto;

public class ConverterManager {

    public static User userDtoConvertToEntity(UserRegisterDto userRegisterDto) {
        User user = new User();

        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(userRegisterDto.getPassword());
        user.setFirstName(userRegisterDto.getFirstName());
        user.setLastName(userRegisterDto.getLastName());
        user.setIdentityNumber(userRegisterDto.getIdentityNumber());
        user.setDateOfBirth(userRegisterDto.getDateOfBirth());
        user.setAddress(userRegisterDto.getAddress());
        user.setPhoneNumber(userRegisterDto.getPhoneNumber());

        return user;
    }

    public static UserGetDto userEntityConvertToDto(User user) {
        UserGetDto userGetDto = new UserGetDto();

        userGetDto.setId(user.getId());
        userGetDto.setEmail(user.getEmail());
        userGetDto.setPassword(user.getPassword());
        userGetDto.setFirstName(user.getFirstName());
        userGetDto.setLastName(user.getLastName());
        userGetDto.setIdentityNumber(user.getIdentityNumber());
        userGetDto.setDateOfBirth(user.getDateOfBirth());
        userGetDto.setAddress(user.getAddress());
        userGetDto.setPhoneNumber(user.getPhoneNumber());

        return userGetDto;
    }
}
