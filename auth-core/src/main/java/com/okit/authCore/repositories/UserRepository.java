package com.okit.authCore.repositories;

import com.okit.authCore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>
{
    @Query(value = "select * from _user where email = ?1 limit 1",nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "select role from users_roles where email = ?1", nativeQuery = true)
    List<String> findRoles(String email);
}
