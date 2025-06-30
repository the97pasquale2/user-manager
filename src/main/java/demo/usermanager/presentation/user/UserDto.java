package demo.usermanager.presentation.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.usermanager.auth.HideFor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    public enum Role {
        OWNER,
        OPERATOR,
        MAINTAINER,
        DEVELOPER,
        REPORTER;

        public static Role fromString(String role) {
            return Role.valueOf(role.toUpperCase());
        }
    }

    private String id;
    private String username;
    private String email;
    private String name;
    private String surname;

    @HideFor(roles={"OPERATOR", "USER"})
    private String taxCode;

    @HideFor(roles={"USER"})
    private List<Role> roles;
}
