package com.okit.authCore.repositories;

import com.okit.authCore.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, String>
{
    @Query(value = "select * from role where name = ?1 limit 1",nativeQuery = true)
    Optional<Role> findByName(String name);

    @Query(value = "select * from role where name in ?1",nativeQuery = true)
    Set<Role> findByName(Set<String> name);
}
