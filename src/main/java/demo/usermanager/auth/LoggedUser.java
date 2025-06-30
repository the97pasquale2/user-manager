package demo.usermanager.auth;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class LoggedUser {
    private String givenUsername;
    private String preferredUsername;
    private String email;
    private List<String> permissions;
    private List<String> roles;


    private LoggedUser() {}
    public static LoggedUser fromTokenClaims(Map<String, Object> tokenClaims) {
        LoggedUser loggedUser = new LoggedUser();

        loggedUser.givenUsername = (String) tokenClaims.get("given_name");
        loggedUser.preferredUsername = (String) tokenClaims.get("preferred_username");
        loggedUser.email = (String) tokenClaims.get("email");
        loggedUser.permissions = new ArrayList<>();

        Map<String, Object> resourceAccess = (Map<String, Object>) tokenClaims.get("resource_access");
        if (resourceAccess != null) {
            Map<String, Object> demoTask = (Map<String, Object>) resourceAccess.get("demo-task");
            if (demoTask != null && demoTask.containsKey("roles")) {
                List<String> resourceRoles = (List<String>) demoTask.get("roles");
                loggedUser.permissions.addAll(resourceRoles);
            }
        }

        Map<String, Object> realmAccess = (Map<String, Object>) tokenClaims.get("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            loggedUser.roles = (List<String>) realmAccess.get("roles");
        }

        return loggedUser;
    }

}
