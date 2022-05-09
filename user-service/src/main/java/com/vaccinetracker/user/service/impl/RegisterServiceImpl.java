package com.vaccinetracker.user.service.impl;

import com.vaccinetracker.config.RegisterConfigData;
import com.vaccinetracker.user.model.UserRequest;
import com.vaccinetracker.user.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {

    private final RegisterConfigData registerConfigData;
    private final RealmResource realmResource;

    public RegisterServiceImpl(RegisterConfigData registerConfigData,
                               @Qualifier("realm-resource-client") RealmResource realmResource) {
        this.registerConfigData = registerConfigData;
        this.realmResource = realmResource;
    }

    @Override
    public void registerUser(UserRequest userRequest) {
        UserRepresentation userRepresentation = getUserRepresentation(userRequest);
        log.info("User with username {} will be created", userRepresentation.getUsername());
        realmResource.users().create(userRepresentation);
    }

    private UserRepresentation getUserRepresentation(UserRequest userRequest) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userRequest.getUsername());
        userRepresentation.setFirstName(userRequest.getFirstName());
        userRepresentation.setLastName(userRequest.getLastName());
        userRepresentation.setEmail(userRequest.getEmail());
        userRepresentation.setCredentials(getCredentials(userRequest.getPassword()));
        userRepresentation.setGroups(getGroups());
        userRepresentation.setEnabled(true);
        userRepresentation.setAttributes(Map.of(registerConfigData.getGovIdAttribute(),
                Collections.singletonList(userRequest.getGovId())));
        return userRepresentation;
    }

    private List<CredentialRepresentation> getCredentials(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        return List.of(credentialRepresentation);
    }

    private List<String> getGroups() {
        GroupRepresentation groupRepresentation = realmResource.getGroupByPath(registerConfigData.getPersonGroupPath());
        return List.of(groupRepresentation.getName());
    }
}
