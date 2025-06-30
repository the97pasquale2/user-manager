package demo.usermanager.auth;

import demo.usermanager.presentation.auth.TokenDto;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public TokenDto getToken(String username, String password) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("https://idpgw.test4mind.com")
                .realm("demo-interview")
                .grantType(OAuth2Constants.PASSWORD)
                .clientId("demo-task")
                .clientSecret("8PDH3fflpbzJyx2rAy39SPB60OuSjeX6")
                .username(username)
                .password(password)
                .build();

        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();

        return TokenDto.builder().token(tokenResponse.getToken()).build();
    }
}
