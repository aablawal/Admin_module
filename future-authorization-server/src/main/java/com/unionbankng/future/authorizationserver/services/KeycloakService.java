package com.unionbankng.future.authorizationserver.services;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final RealmResource keycloakRealmResource;

    public void enableKeyCloakUser(String userUUID){
        //enable user on keycloak acc
        UsersResource usersResource = keycloakRealmResource.users();
        UserResource userResource = usersResource.get(userUUID);

        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        userResource.update(userRepresentation);
    }
}
