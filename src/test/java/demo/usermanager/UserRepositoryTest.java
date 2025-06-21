package demo.usermanager;

import demo.usermanager.model.role.Role;
import demo.usermanager.model.role.RoleRepository;
import demo.usermanager.model.user.User;
import demo.usermanager.model.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    Map<String, Role> roleEntities = new HashMap<>();

    @BeforeEach
    void init() {
        userRepository.deleteAll();
        roleRepository.deleteAll();

        final String[] rolesName = {"OWNER", "OPERATOR", "MAINTAINER", "DEVELOPER", "REPORTER"};
        for (String roleName : rolesName) {
            Role role = roleRepository.save(Role.builder().name(roleName).build());
            roleEntities.put(roleName, role);
        }
    }

    @Test
    void saveJustUserAndFindById() {
        User userSaved = userRepository.save(User
                .builder()
                .email("mario.rossi@gmail.com")
                .name("Mario")
                .surname("Rossi")
                .taxCode("MRARSS97A27F471S")
                .build());

        final String id = userSaved.getId();
        System.out.println("stored id " + id);
        User userFound = userRepository.findById(id).orElseThrow();
        assertEquals("Mario", userFound.getName());
        assertEquals("Rossi", userFound.getSurname());
        assertEquals("MRARSS97A27F471S", userFound.getTaxCode());
        assertEquals("mario.rossi@gmail.com", userFound.getEmail());
    }

    @Test
    void saveUserAndRolesAndFindById() {
        User userSaved = userRepository.save(User
                .builder()
                .email("mario.rossi@gmail.com")
                .name("Mario")
                .surname("Rossi")
                .taxCode("MRARSS97A27F471S")
                 .roles(Arrays.asList(roleEntities.get("OWNER")))
                .build());

        userRepository.save(userSaved);
        final String id = userSaved.getId();
        System.out.println("stored id " + id);
        User userFound = userRepository.findById(id).orElseThrow();
        assertEquals("Mario", userFound.getName());
        assertEquals("Rossi", userFound.getSurname());
        assertEquals("MRARSS97A27F471S", userFound.getTaxCode());
        assertEquals("mario.rossi@gmail.com", userFound.getEmail());
        assertEquals(1, userFound.getRoles().size());
        assertEquals("OWNER", userFound.getRoles().get(0).getName());
    }

    @Test
    void updateUserAndRolesAndFindById() {
        User userSaved = userRepository.save(User
                .builder()
                .email("mario.rossi@gmail.com")
                .name("Mario")
                .surname("Rossi")
                .taxCode("MRARSS97A27F471S")
                .roles(Arrays.asList(roleEntities.get("OWNER")))
                .build());

        userRepository.save(userSaved);
        final String id = userSaved.getId();
        User userFoundBeforeUpdate = userRepository.findById(id).orElseThrow();

        //Changing user and roles...
        userSaved.setSurname("RossiChanged");
        userSaved.setRoles(Arrays.asList(roleEntities.get("DEVELOPER")));
        userRepository.save(userSaved);


        User userFoundAfterUpdate = userRepository.findById(id).orElseThrow();

        assertEquals("Rossi", userFoundBeforeUpdate.getSurname());
        assertEquals("RossiChanged", userFoundAfterUpdate.getSurname());
        assertEquals(1, userFoundAfterUpdate.getRoles().size());
        assertEquals("DEVELOPER", userFoundAfterUpdate.getRoles().get(0).getName());
    }

    @Test
    void deleteUserAndFindById() {
        User userSaved = userRepository.save(User
                .builder()
                .email("mario.to.delete@gmail.com")
                .name("Mario")
                .surname("ToDelete")
                .taxCode("MRARSS97A27F471S")
                .roles(Arrays.asList(roleEntities.get("OWNER")))
                .build());

        userRepository.delete(userSaved);

        User userFound = userRepository.findById(userSaved.getId()).orElse(null);
        assertNull(userFound);

        //ROLE MUST NOT BE DELETED!
        roleRepository.findByName("OWNER").orElseThrow();
    }
}
