package com.gorkem.demoPLPostgres.business.abstracts;

import com.gorkem.demoPLPostgres.core.results.DataResult;
import com.gorkem.demoPLPostgres.core.results.Result;
import com.gorkem.demoPLPostgres.entity.dtos.UserGetDto;
import com.gorkem.demoPLPostgres.entity.dtos.UserRegisterDto;
import com.gorkem.demoPLPostgres.entity.dtos.UserUpdateDto;

import java.util.List;

public interface Db2UserService {

    DataResult<UserGetDto> get(String identityNumber);

    DataResult<List<UserGetDto>> getAll();

    DataResult<UserRegisterDto> add(UserRegisterDto userRegisterDto);

    Result delete(String identityNumber);

    DataResult<UserGetDto> update(UserUpdateDto userUpdateDto, String identityNumber);
}
