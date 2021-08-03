package com.gorkem.mongodatabases.business.concretes;

import com.gorkem.mongodatabases.business.abstracts.UserLocationService;
import com.gorkem.mongodatabases.converters.ConverterManager;
import com.gorkem.mongodatabases.core.results.*;
import com.gorkem.mongodatabases.core.constants.*;
import com.gorkem.mongodatabases.dataAccess.UserLocationRepository;
import com.gorkem.mongodatabases.models.UserLocation;
import com.gorkem.mongodatabases.models.dtos.UserLocationAddDto;
import com.gorkem.mongodatabases.models.dtos.UserLocationGetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserLocationManager implements UserLocationService {

    private final UserLocationRepository userLocationRepository;

    @Autowired
    public UserLocationManager(UserLocationRepository userLocationRepository) {
        this.userLocationRepository = userLocationRepository;
    }

    @Override
    public DataResult<UserLocationGetDto> get(String identityNumber) {
        UserLocation userLocation = this.userLocationRepository.getByIdentityNumber(identityNumber);

        if (userLocation != null) {

            UserLocationGetDto userLocationGetDto = ConverterManager.entityConvertToDto(userLocation);
            return new SuccessDataResult<>(Message.SUCCESSFULLY_LİSTED, userLocationGetDto);

        } else {
            return new ErrorDataResult<>(Message.LIST_FAILED);
        }
    }

    @Override
    public DataResult<List<UserLocationGetDto>> getAll() {
        List<UserLocation> userLocations = this.userLocationRepository.findAll();

        if (!userLocations.isEmpty()) {
            List<UserLocationGetDto> userLocationGetDtos = new ArrayList<>();

            for (UserLocation userLocation : userLocations) {

                UserLocationGetDto userLocationGetDto = ConverterManager.entityConvertToDto(userLocation);

                userLocationGetDtos.add(userLocationGetDto);
            }
            return new SuccessDataResult<>(Message.SUCCESSFULLY_LİSTED, userLocationGetDtos);
        } else {
            return new ErrorDataResult<>(Message.LIST_FAILED);
        }
    }

    @Override
    public Result add(UserLocationAddDto userLocationAddDto) {

        UserLocation userLocation = ConverterManager.dtoConvertToEntity(userLocationAddDto);

        this.userLocationRepository.save(userLocation);

        return new SuccessResult(Message.SUCCESSFULLY_ADDED);
    }
}
