package demo.usermanager;

import demo.usermanager.model.role.Role;
import demo.usermanager.model.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    Map<String, Role> roleEntities = new HashMap<>();

    @BeforeEach
    void init() {
        final String[] rolesName = {"OWNER", "OPERATOR", "MAINTAINER", "DEVELOPER", "REPORTER"};
        for (String roleName : rolesName) {
            Role role = roleRepository.save(Role.builder().name(roleName).build());
            roleEntities.put(roleName, role);
        }
    }

    @Test
    void saveTwoIdenticalRoles() {
        assertThrows(Exception.class, () -> {
            roleRepository.save(Role.builder().name("ROLE_TEST").build());
            roleRepository.save(Role.builder().name("ROLE_TEST").build());
        });
    }
}
