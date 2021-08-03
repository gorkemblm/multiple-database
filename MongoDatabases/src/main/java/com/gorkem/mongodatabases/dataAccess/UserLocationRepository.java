package com.gorkem.mongodatabases.dataAccess;

import com.gorkem.mongodatabases.models.UserLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserLocationRepository extends MongoRepository<UserLocation, Integer> {

    UserLocation getByIdentityNumber(String identityNumber);
}
