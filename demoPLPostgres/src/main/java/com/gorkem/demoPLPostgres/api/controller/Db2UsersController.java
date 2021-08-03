package com.gorkem.demoPLPostgres.api.controller;

import com.gorkem.demoPLPostgres.business.abstracts.Db2UserService;
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
@RequestMapping(value = "/api/users/Database-2")
public class Db2UsersController {

    private final Db2UserService db2UserService;

    @Autowired
    public Db2UsersController(Db2UserService db2UserService) {
        this.db2UserService = db2UserService;
    }

    @GetMapping("/get")
    public ResponseEntity<DataResult<UserGetDto>> get(@RequestParam int id) {
        var result = this.db2UserService.get(id);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<DataResult<List<UserGetDto>>> getAll() {
        var result = this.db2UserService.getAll();

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<DataResult<UserRegisterDto>> add(@RequestBody UserRegisterDto userRegisterDto) {
        var result = this.db2UserService.add(userRegisterDto);

        if (result.isSuccess() && result.getData() != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Result> delete(@RequestParam int id) {
        var result = this.db2UserService.delete(id);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<DataResult<UserGetDto>> update(@RequestBody UserUpdateDto userUpdateDto, @RequestParam int id) {
        var result = this.db2UserService.update(userUpdateDto, id);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
