package br.com.intelibank.service.impl;

import br.com.intelibank.service.KeycloakService;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloak;

    private final String realm;

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Autowired
    public KeycloakServiceImpl(Keycloak keycloak, @Value("${keycloak.realm}") String realm) {
        this.keycloak = keycloak;
        this.realm = realm;

        if (this.keycloak == null) {
            throw new IllegalStateException("Keycloak instance is null");
        }
    }


    @Override
    public String createUser(String username, String password, String email) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEmailVerified(false);
        user.setEnabled(true);

        try {
            Response response = keycloak.realm(realm).users().create(user);
            if(response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new RuntimeException("failed to create user");
            }

            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(password);
            credentialRepresentation.setTemporary(false);

            keycloak.realm(realm).users().get(userId).resetPassword(credentialRepresentation);

            return userId;
        }catch(Exception e) {
            throw new RuntimeException("failed to create user", e);
        }
    }

    @Override
    public String login(String username, String password) {
        try {
            Keycloak userKeycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(username)
                    .password(password)
                    .build();
            return userKeycloak.tokenManager().getAccessToken().getToken();
        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate user", e);
        }
    }
}
