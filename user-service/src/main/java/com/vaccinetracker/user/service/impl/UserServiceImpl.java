package com.vaccinetracker.user.service.impl;

import com.vaccinetracker.booking.model.UserRequest;
import com.vaccinetracker.user.query.model.UserQueryWebClientResponse;
import com.vaccinetracker.user.service.BookingQueryWebClient;
import com.vaccinetracker.user.service.UserService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;
    private final BookingQueryWebClient bookingQueryWebClient;

    public UserServiceImpl(@Qualifier("keycloak-client") Keycloak keycloak,
                           BookingQueryWebClient bookingQueryWebClient) {
        this.keycloak = keycloak;
        this.bookingQueryWebClient = bookingQueryWebClient;
    }

    @Override
    public void registerUser(UserRequest userRequest) {
        UsersResource usersResource = (UsersResource) keycloak;
        List<UserRepresentation> result = usersResource.search(userRequest.getUsername());
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userRequest.getUsername());
        userRepresentation.setFirstName(userRequest.getFirstName());
        userRepresentation.setLastName(userRequest.getLastName());
        userRepresentation.setAttributes(
                Collections.singletonMap("gov_id", Collections.singletonList(userRequest.getGovId())));
        usersResource.create(userRepresentation);
    }

    @Override
    public List<UserQueryWebClientResponse> getBookingByGovId(String govId) {
        return bookingQueryWebClient.getBookingByGovId(govId);
    }
}
