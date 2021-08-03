package com.gorkem.mongodatabases.business.abstracts;

import com.gorkem.mongodatabases.core.results.DataResult;
import com.gorkem.mongodatabases.core.results.Result;
import com.gorkem.mongodatabases.models.dtos.UserLocationAddDto;
import com.gorkem.mongodatabases.models.dtos.UserLocationGetDto;

import java.util.List;

public interface UserLocationService {

    DataResult<UserLocationGetDto> get(String identityNumber);

    DataResult<List<UserLocationGetDto>> getAll();

    Result add(UserLocationAddDto userLocationAddDto);

    Result delete(String identityNumber);
}
