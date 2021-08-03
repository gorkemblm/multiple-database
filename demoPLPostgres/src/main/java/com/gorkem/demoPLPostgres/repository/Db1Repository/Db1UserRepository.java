package com.gorkem.demoPLPostgres.repository.Db1Repository;

import com.gorkem.demoPLPostgres.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("Db1UserRepository")
public interface Db1UserRepository extends JpaRepository<User, Integer> {

    User getByIdentityNumber(String identityNumber);
}
