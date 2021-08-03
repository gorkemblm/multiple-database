package com.gorkem.demoPLPostgres.repository.Db3Repository;

import com.gorkem.demoPLPostgres.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Db3UserRepository extends JpaRepository<User, Integer> {

    User getByIdentityNumber(String identityNumber);
}
