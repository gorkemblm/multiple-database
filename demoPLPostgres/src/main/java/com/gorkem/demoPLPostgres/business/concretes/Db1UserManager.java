package com.gorkem.demoPLPostgres.business.concretes;

import com.gorkem.demoPLPostgres.business.abstracts.Db1UserService;
import com.gorkem.demoPLPostgres.converter.ConverterManager;
import com.gorkem.demoPLPostgres.core.constants.Message;
import com.gorkem.demoPLPostgres.core.results.*;
import com.gorkem.demoPLPostgres.core.log.*;
import com.gorkem.demoPLPostgres.entity.User;
import com.gorkem.demoPLPostgres.entity.dtos.UserGetDto;
import com.gorkem.demoPLPostgres.entity.dtos.UserRegisterDto;
import com.gorkem.demoPLPostgres.entity.dtos.UserUpdateDto;
import com.gorkem.demoPLPostgres.repository.Db1Repository.Db1UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class Db1UserManager implements Db1UserService {

    private final Db1UserRepository db1UserRepository;

    @Autowired
    public Db1UserManager(Db1UserRepository db1UserRepository) {
        this.db1UserRepository = db1UserRepository;
    }

    @Override
    public DataResult<UserGetDto> get(String identityNumber) {
        User user = null;

        try {
            user = this.db1UserRepository.getByIdentityNumber(identityNumber);

            if (user.getIdentityNumber() == null) {
                throw new Exception();
            } else {
                UserGetDto userGetDto = ConverterManager.userEntityConvertToDto(user);
                log.info(String.valueOf(new CustomLogger(HttpStatus.OK.value(), Message.SUCCESSFULLY_LİSTED, user)));
                return new SuccessDataResult<>(Message.SUCCESSFULLY_LİSTED, userGetDto);
            }
        } catch (Exception e) {
            log.error(String.valueOf(new CustomLogger(HttpStatus.BAD_REQUEST.value(), Message.LIST_FAILED, e.getMessage())));
            return new ErrorDataResult<>(Message.LIST_FAILED);
        }
    }

    @Override
    public DataResult<List<UserGetDto>> getAll() {
        List<User> userList = null;

        try {
            userList = this.db1UserRepository.findAll();

            if (userList.isEmpty()) {
                throw new Exception();
            } else {
                List<UserGetDto> dtoArray = new ArrayList<>();

                for (User u : userList) {

                    UserGetDto userGetDto = ConverterManager.userEntityConvertToDto(u);

                    dtoArray.add(userGetDto);
                }
                log.info(String.valueOf(new CustomLogger(HttpStatus.OK.value(), Message.SUCCESSFULLY_LİSTED, dtoArray)));
                return new SuccessDataResult<>(Message.SUCCESSFULLY_LİSTED, dtoArray);
            }

        } catch (Exception e) {
            log.error(String.valueOf(new CustomLogger(HttpStatus.BAD_REQUEST.value(), Message.LIST_FAILED, e.getMessage())));
            return new ErrorDataResult<>(Message.LIST_FAILED);
        }
    }

    @Override
    public DataResult<UserRegisterDto> add(UserRegisterDto userRegisterDto) {
        User user = ConverterManager.userDtoConvertToEntity(userRegisterDto);

        try {
            this.db1UserRepository.save(user);

            log.info(String.valueOf(new CustomLogger(HttpStatus.OK.value(), Message.SUCCESSFULLY_ADDED, user)));
            return new SuccessDataResult(Message.SUCCESSFULLY_ADDED, user);

        } catch (Exception e) {
            log.error(String.valueOf(new CustomLogger(HttpStatus.BAD_REQUEST.value(), Message.ADD_FAILED, e.getMessage())));
            return new ErrorDataResult<>(Message.ADD_FAILED);
        }
    }

    @Override
    public Result delete(String identityNumber) {
        User value = null;

        try {
            value = this.db1UserRepository.getByIdentityNumber(identityNumber);

            if (value.getIdentityNumber() == null) {
                throw new Exception();
            } else {
                this.db1UserRepository.delete(value);
                log.info(String.valueOf(new CustomLogger(HttpStatus.OK.value(), Message.SUCCESSFULLY_DELETED, value)));
                return new SuccessResult(Message.SUCCESSFULLY_DELETED);
            }
        } catch (Exception e) {
            log.error(String.valueOf(new CustomLogger(HttpStatus.BAD_REQUEST.value(), Message.DELETE_FAILED, e.getMessage())));
            return new ErrorResult(Message.DELETE_FAILED);
        }
    }

    @Override
    public DataResult<UserGetDto> update(UserUpdateDto userUpdateDto, String identityNumber) {
        User user = null;

        try {
            user = this.db1UserRepository.getByIdentityNumber(identityNumber);

            if (user.getIdentityNumber() == null) {
                throw new Exception();
            } else {

                user.setEmail(userUpdateDto.getEmail());
                user.setPassword(userUpdateDto.getPassword());
                user.setFirstName(userUpdateDto.getFirstName());
                user.setLastName(userUpdateDto.getLastName());
                user.setIdentityNumber(userUpdateDto.getIdentityNumber());
                user.setDateOfBirth(userUpdateDto.getDateOfBirth());
                user.setPhoneNumber(userUpdateDto.getPhoneNumber());

                this.db1UserRepository.save(user);
                UserGetDto userGetDto = ConverterManager.userEntityConvertToDto(user);

                log.info(String.valueOf(new CustomLogger(HttpStatus.OK.value(), Message.SUCCESSFULLY_DELETED, userGetDto)));
                return new SuccessDataResult<>(Message.SUCCESSFULLY_UPDATED, userGetDto);
            }
        } catch (Exception e) {
            log.error(String.valueOf(new CustomLogger(HttpStatus.BAD_REQUEST.value(), Message.DELETE_FAILED, e.getMessage())));
            return new ErrorDataResult<>(Message.UPDATE_FAILED);
        }
    }
}
