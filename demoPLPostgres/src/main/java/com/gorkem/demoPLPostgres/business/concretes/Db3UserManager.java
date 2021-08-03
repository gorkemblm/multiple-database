package com.gorkem.demoPLPostgres.business.concretes;

import com.gorkem.demoPLPostgres.business.abstracts.Db3UserService;
import com.gorkem.demoPLPostgres.converter.ConverterManager;
import com.gorkem.demoPLPostgres.core.constants.Message;
import com.gorkem.demoPLPostgres.core.results.*;
import com.gorkem.demoPLPostgres.entity.User;
import com.gorkem.demoPLPostgres.entity.dtos.UserGetDto;
import com.gorkem.demoPLPostgres.entity.dtos.UserRegisterDto;
import com.gorkem.demoPLPostgres.entity.dtos.UserUpdateDto;
import com.gorkem.demoPLPostgres.repository.Db3Repository.Db3UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Db3UserManager implements Db3UserService {

    private final Db3UserRepository db3UserRepository;

    @Autowired
    public Db3UserManager(Db3UserRepository db3UserRepository) {
        this.db3UserRepository = db3UserRepository;
    }

    @Override
    public DataResult<UserGetDto> get(int id) {
        Optional<User> user = this.db3UserRepository.findById(id);

        if (!user.isEmpty()) {
            UserGetDto userGetDto = ConverterManager.userEntityConvertToDto(user.get());
            return new SuccessDataResult<>(Message.SUCCESSFULLY_LİSTED, userGetDto);
        } else {
            return new ErrorDataResult<>(Message.LIST_FAILED);
        }
    }

    @Override
    public DataResult<List<UserGetDto>> getAll() {
        List<User> userList = this.db3UserRepository.findAll();

        if (!userList.isEmpty()) {
            List<UserGetDto> dtoArray = new ArrayList<>();

            for (User u : userList) {

                UserGetDto userGetDto = ConverterManager.userEntityConvertToDto(u);

                dtoArray.add(userGetDto);
            }
            return new SuccessDataResult<>(Message.SUCCESSFULLY_LİSTED, dtoArray);
        } else {
            return new ErrorDataResult<>(Message.LIST_FAILED);
        }
    }

    @Override
    public DataResult<UserRegisterDto> add(UserRegisterDto userRegisterDto) {

        User user = ConverterManager.userDtoConvertToEntity(userRegisterDto);

        this.db3UserRepository.save(user);

        return new SuccessDataResult(Message.SUCCESSFULLY_ADDED, user);
    }

    @Override
    public Result delete(int id) {
        boolean value = this.db3UserRepository.existsById(id);

        if (value) {

            this.db3UserRepository.deleteById(id);
            return new SuccessResult(Message.SUCCESSFULLY_DELETED);

        } else {
            return new ErrorResult(Message.DELETE_FAILED);
        }
    }

    @Override
    public DataResult<UserGetDto> update(UserUpdateDto userUpdateDto, int id) {

        User user = this.db3UserRepository.getById(id);

        if (user != null) {

            user.setEmail(userUpdateDto.getEmail());
            user.setPassword(userUpdateDto.getPassword());
            user.setFirstName(userUpdateDto.getFirstName());
            user.setLastName(userUpdateDto.getLastName());
            user.setIdentityNumber(userUpdateDto.getIdentityNumber());
            user.setDateOfBirth(userUpdateDto.getDateOfBirth());
            user.setPhoneNumber(userUpdateDto.getPhoneNumber());

            this.db3UserRepository.save(user);
            UserGetDto userGetDto = ConverterManager.userEntityConvertToDto(user);

            return new SuccessDataResult<>(Message.SUCCESSFULLY_UPDATED, userGetDto);

        } else {
            return new ErrorDataResult<>(Message.UPDATE_FAILED);
        }
    }
}
