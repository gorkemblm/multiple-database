package com.gorkem.demoPLPostgres.api.controller;

import com.gorkem.demoPLPostgres.business.abstracts.Db1UserService;
import com.gorkem.demoPLPostgres.core.results.DataResult;
import com.gorkem.demoPLPostgres.core.results.Result;
import com.gorkem.demoPLPostgres.entity.dtos.UserGetDto;
import com.gorkem.demoPLPostgres.entity.dtos.UserRegisterDto;
import com.gorkem.demoPLPostgres.entity.dtos.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users/Database-1")
public class Db1UsersController {

    private final Db1UserService db1UserService;

    @Autowired
    public Db1UsersController(Db1UserService db1UserService) {
        this.db1UserService = db1UserService;
    }

    @GetMapping("/get/{identityNumber}")
    @ResponseBody
    public ResponseEntity<DataResult<UserGetDto>> get(@PathVariable("identityNumber") String identityNumber) {
        var result = this.db1UserService.get(identityNumber);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<DataResult<List<UserGetDto>>> getAll() {
        var result = this.db1UserService.getAll();

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<DataResult<UserRegisterDto>> add(@RequestBody UserRegisterDto userRegisterDto) {
        var result = this.db1UserService.add(userRegisterDto);

        if (result.isSuccess() && result.getData() != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{identityNumber}")
    @ResponseBody
    public ResponseEntity<Result> delete(@PathVariable("identityNumber") String identityNumber) {
        var result = this.db1UserService.delete(identityNumber);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{identityNumber}")
    @ResponseBody
    public ResponseEntity<DataResult<UserGetDto>> update(@RequestBody UserUpdateDto userUpdateDto, @PathVariable("identityNumber") String identityNumber) {
        var result = this.db1UserService.update(userUpdateDto, identityNumber);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
