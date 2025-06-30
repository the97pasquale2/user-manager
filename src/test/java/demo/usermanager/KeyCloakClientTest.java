package demo.usermanager;

import org.junit.jupiter.api.Test;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class KeyCloakClientTest {

    @Test
    void gettingToken() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("https://idpgw.test4mind.com")
                .realm("demo-interview")
                .grantType(OAuth2Constants.PASSWORD)
                .clientId("demo-task")
                .clientSecret("8PDH3fflpbzJyx2rAy39SPB60OuSjeX6")
                .username("admin_user")
                .password("admin_user_2024!")
                .build();

        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();

        System.out.println("Access Token: " + tokenResponse.getToken());
    }
}
