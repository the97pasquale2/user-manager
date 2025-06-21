package demo.usermanager.config;

import demo.usermanager.model.role.Role;
import demo.usermanager.model.role.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
public class DatabaseInitializer {

    private final RoleRepository roleRepository;

    private final List<String> roles = List.of("OWNER", "OPERATOR", "MAINTAINER", "DEVELOPER", "REPORTER");
    @PostConstruct
    public void init() {
        for(String role : roles) {
            Role roleInDb = roleRepository.findByName(role).orElse(null);
            if(roleInDb == null) {
                roleRepository.save(Role.builder().name(role).build());
            }
        }
    }

}
