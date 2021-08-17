package com.gorkem.mongodatabases.api.controller;

import com.gorkem.mongodatabases.DatabaseManager;
import com.gorkem.mongodatabases.business.abstracts.UserLocationService;
import com.gorkem.mongodatabases.core.results.DataResult;
import com.gorkem.mongodatabases.core.results.Result;
import com.gorkem.mongodatabases.models.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user-locations")
public class UserLocationsController {

    //Bu call api leri ve db ismi daha global bir yere taşınmalı !
    //static olarak çağrılacak bir yere taşınabilir..
    public static String CALL_REST_API = "http://localhost:8081/api/users/";

    private final UserLocationService userLocationService;

    public static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public UserLocationsController(UserLocationService userLocationService) {
        this.userLocationService = userLocationService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<DataResult<List<UserLocationGetDto>>> getAll() {
        var result = this.userLocationService.getAll();

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Result> add(@RequestBody UserLocationAddDto userLocationAddDto) {
        var result = this.userLocationService.add(userLocationAddDto);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<DataResult<UserLocationGetDto>> get(@RequestParam String identityNumber) {
        var result = this.userLocationService.get(identityNumber);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    //For PostgreSQL
    @PostMapping("/add-user")
    public ResponseEntity<DataResult<UserRegisterDto>> addUser(@RequestBody UserRegisterDto userRegisterDto) {
        String dbName = DatabaseManager.findTheDBWithLoadBalance(this.userLocationService.getAll().getData());

        DataResult result = restTemplate.postForObject((CALL_REST_API + dbName + "/add"),
                userRegisterDto,
                DataResult.class);

        if (result.isSuccess()) {
            UserLocationAddDto userLocationAddDto = new UserLocationAddDto(userRegisterDto.getIdentityNumber(), dbName);

            this.userLocationService.add(userLocationAddDto);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-user/{identityNumber}")
    @ResponseBody
    public ResponseEntity<DataResult<UserGetDto>> getUser(@PathVariable("identityNumber") String identityNumber) {

        DataResult<UserLocationGetDto> userLocation = this.userLocationService.get(identityNumber);

        if (userLocation.isSuccess()) {

            String dbName = userLocation.getData().getDatabaseAddress();

            DataResult result = restTemplate.getForObject(CALL_REST_API + dbName + "/get/{identityNumber}",
                    DataResult.class,
                    identityNumber);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-users/{databaseName}")
    @ResponseBody
    public ResponseEntity<DataResult<List<UserGetDto>>> getUsers(@PathVariable("databaseName") String databaseName) {

        DataResult result = restTemplate.getForObject(CALL_REST_API + databaseName + "/get-all",
                DataResult.class,
                databaseName);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-user/{identityNumber}")
    @ResponseBody
    public ResponseEntity<DataResult<UserGetDto>> updateUser(@RequestBody UserUpdateDto userUpdateDto, @PathVariable String identityNumber) {

        DataResult<UserLocationGetDto> userLocation = this.userLocationService.get(identityNumber);

        String dbName = userLocation.getData().getDatabaseAddress();

        ResponseEntity<DataResult> result = restTemplate.exchange(CALL_REST_API + dbName + "/update/{identityNumber}",
                HttpMethod.PUT, new HttpEntity<>(userUpdateDto),
                DataResult.class,
                identityNumber);

        if (result.getBody().isSuccess()) {
            return new ResponseEntity<>(result.getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-user/{identityNumber}")
    @ResponseBody
    public ResponseEntity<Result> deleteUser(@PathVariable("identityNumber") String identityNumber) {

        DataResult<UserLocationGetDto> userLocation = this.userLocationService.get(identityNumber);

        if (userLocation.isSuccess()) {
            String dbName = userLocation.getData().getDatabaseAddress();

            restTemplate.delete(CALL_REST_API + dbName + "/delete/{identityNumber}",
                    identityNumber);

            this.userLocationService.delete(identityNumber);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
