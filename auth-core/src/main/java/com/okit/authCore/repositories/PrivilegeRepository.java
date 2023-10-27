package com.okit.authCore.repositories;

import com.okit.authCore.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, String>
{
    @Query(value = "select * from privilege where name = ?1 limit 1", nativeQuery = true)
    Optional<Privilege> findByName(String name);
}
