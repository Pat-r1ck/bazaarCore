package com.okit.profileservice.repositories;

import com.okit.profileservice.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, String>
{
    @Query(value = "select * from user_profile where email = ?1 limit 1", nativeQuery = true)
    Optional<UserProfile> findByEmail(String email);
}
