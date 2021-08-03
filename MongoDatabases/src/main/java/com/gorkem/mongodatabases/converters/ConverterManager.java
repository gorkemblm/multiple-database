package com.gorkem.mongodatabases.converters;

import com.gorkem.mongodatabases.models.UserLocation;
import com.gorkem.mongodatabases.models.dtos.UserLocationAddDto;
import com.gorkem.mongodatabases.models.dtos.UserLocationGetDto;

public class ConverterManager {

    public static UserLocationGetDto entityConvertToDto(UserLocation userLocation) {

        UserLocationGetDto userLocationGetDto = new UserLocationGetDto();

        userLocationGetDto.setIdentityNumber(userLocation.getIdentityNumber());
        userLocationGetDto.setDatabaseAddress(userLocation.getDatabaseAddress());

        return userLocationGetDto;
    }

    public static UserLocation dtoConvertToEntity(UserLocationAddDto userLocationAddDto) {

        UserLocation userLocation = new UserLocation();

        userLocation.setIdentityNumber(userLocationAddDto.getIdentityNumber());
        userLocation.setDatabaseAddress(userLocationAddDto.getDatabaseAddress());

        return userLocation;
    }
}
