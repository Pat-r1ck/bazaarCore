package com.okit.resourceservice.repositories;

import com.okit.resourceservice.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String>
{
    @Query(value = "select * from user_profile where email = ?1 limit 1", nativeQuery = true)
    Optional<UserProfile> findByEmail(String email);
}
