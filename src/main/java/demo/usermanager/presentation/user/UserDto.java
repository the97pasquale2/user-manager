package demo.usermanager.presentation.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    //TODO Forse va spostato altrove?
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
    private String taxCode;
    private List<Role> roles;
}
