package demo.usermanager.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class IdentityProvider {

    public LoggedUser getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth instanceof JwtAuthenticationToken jwt) {
            return LoggedUser.fromTokenClaims(jwt.getToken().getClaims());
        }

        throw new NoLoggedUserException("Error while trying to get logged user. No authentication found.");
    }
}
