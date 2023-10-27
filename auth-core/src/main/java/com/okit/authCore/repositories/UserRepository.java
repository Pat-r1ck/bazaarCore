package com.okit.authCore.repositories;

import com.okit.authCore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>
{
    @Query(value = "select * from _user where username = ?1 limit 1",nativeQuery = true)
    Optional<User>  findByUsername(String username);
}
