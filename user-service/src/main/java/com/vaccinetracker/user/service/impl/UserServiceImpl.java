package com.vaccinetracker.user.service.impl;

import com.vaccinetracker.user.model.UserRequest;
import com.vaccinetracker.user.query.model.UserQueryWebClientResponse;
import com.vaccinetracker.user.service.BookingQueryWebClient;
import com.vaccinetracker.user.service.UserService;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final RealmResource realmResource;
    private final BookingQueryWebClient bookingQueryWebClient;

    public UserServiceImpl(@Qualifier("realm-resource-client") RealmResource realmResource,
                           BookingQueryWebClient bookingQueryWebClient) {
        this.realmResource = realmResource;
        this.bookingQueryWebClient = bookingQueryWebClient;
    }

    @Override
    public void registerUser(UserRequest userRequest) {
        UserRepresentation userRepresentation = getUserRepresentation(userRequest);
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
        userRepresentation.setAttributes(Map.of("gov_id", Collections.singletonList(userRequest.getGovId())));
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
        GroupRepresentation groupRepresentation = realmResource.getGroupByPath("/person_group");
        return List.of(groupRepresentation.getName());
    }

    @Override
    public List<UserQueryWebClientResponse> getBookingByGovId(String govId) {
        return bookingQueryWebClient.getBookingByGovId(govId);
    }
}
