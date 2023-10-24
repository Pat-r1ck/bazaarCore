package com.okit.authCore.config;

import com.okit.authCore.models.Privilege;
import com.okit.authCore.models.Role;
import com.okit.authCore.repositories.PrivilegeRepository;
import com.okit.authCore.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>
{
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        Privilege privilegeRead = Privilege.builder()
                .name("READ_PRIVILEGE")
                .build();
        Privilege privilegeWrite = Privilege.builder()
                .name("WRITE_PRIVILEGE")
                .build();
        Privilege privilegeDelete = Privilege.builder()
                .name("DELETE_PRIVILEGE")
                .build();

        Set<Privilege> adminPrivilege = new HashSet<>();
        Set<Privilege> userPrivilege = new HashSet<>();

        adminPrivilege.add(privilegeRead);
        adminPrivilege.add(privilegeWrite);

        userPrivilege.add(privilegeRead);

        Role roleAdmin = Role.builder()
                .name("ROLE_ADMIN")
                .privileges(adminPrivilege)
                .build();

        Role roleUser = Role.builder()
                .name("ROLE_USER")
                .privileges(userPrivilege)
                .build();

        privilegeRepository.saveAll(Arrays.asList(privilegeRead, privilegeWrite, privilegeDelete));
        roleRepository.saveAll(Arrays.asList(roleUser, roleAdmin));
    }

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PrivilegeRepository privilegeRepository;
}
