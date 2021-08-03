package com.gorkem.demoPLPostgres.repository.Db2Repository;

import com.gorkem.demoPLPostgres.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Db2UserRepository extends JpaRepository<User, Integer> {
}
