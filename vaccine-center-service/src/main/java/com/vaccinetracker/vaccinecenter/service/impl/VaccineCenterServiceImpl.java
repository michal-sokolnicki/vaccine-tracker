package com.vaccinetracker.vaccinecenter.service.impl;

import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.entity.VaccineStock;
import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import com.vaccinetracker.vaccinecenter.model.UserRequest;
import com.vaccinetracker.vaccinecenter.model.VaccineCenterRequest;
import com.vaccinetracker.vaccinecenter.query.model.VaccineCenterQueryWebClientResponse;
import com.vaccinetracker.vaccinecenter.service.QueryWebClient;
import com.vaccinetracker.vaccinecenter.service.VaccineCenterService;
import com.vaccinetracker.vaccinecenter.service.transformer.ResponseModelToIndexModelTransformer;
import com.vaccinetracker.vaccinecenter.service.transformer.VaccineCenterToIndexModelTransformer;
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
public class VaccineCenterServiceImpl implements VaccineCenterService {

    private static final String VACCINE_CENTER_ID_ATTRIBUTE = "vaccine_center_id";
    private static final String VACCINE_CENTER_GROUP_PATH = "/vaccine_center_group";

    private final RealmResource realmResource;
    private final VaccineCenterToIndexModelTransformer vaccineCenterToIndexModelTransformer;
    private final QueryWebClient queryWebClient;
    private final ResponseModelToIndexModelTransformer responseModelToIndexModelTransformer;
    private final ElasticIndexClient<VaccineCenterIndexModel> elasticIndexClient;

    public VaccineCenterServiceImpl(@Qualifier("realm-resource-client") RealmResource realmResource,
                                    VaccineCenterToIndexModelTransformer vaccineCenterToIndexModelTransformer,
                                    QueryWebClient queryWebClient,
                                    ResponseModelToIndexModelTransformer responseModelToIndexModelTransformer,
                                    ElasticIndexClient<VaccineCenterIndexModel> elasticIndexClient) {
        this.realmResource = realmResource;
        this.vaccineCenterToIndexModelTransformer = vaccineCenterToIndexModelTransformer;
        this.queryWebClient = queryWebClient;
        this.responseModelToIndexModelTransformer = responseModelToIndexModelTransformer;
        this.elasticIndexClient = elasticIndexClient;
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
        userRepresentation.setAttributes(Map.of(VACCINE_CENTER_ID_ATTRIBUTE,
                Collections.singletonList(userRequest.getVaccineCenterId())));
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
        GroupRepresentation groupRepresentation = realmResource.getGroupByPath(VACCINE_CENTER_GROUP_PATH);
        return List.of(groupRepresentation.getName());
    }

    @Override
    public void updateStock(String id, VaccineCenterRequest vaccineCenterRequest) {
        VaccineCenterIndexModel vaccineCenterIndexModel =
                vaccineCenterToIndexModelTransformer.getVaccineCenterIndexModel(id, vaccineCenterRequest);
        elasticIndexClient.save(vaccineCenterIndexModel);
        log.info("Document with id: {} has been updated in elasticsearch", id);
    }

    @Override
    public void processMessages(List<BookingAvroModel> messages) {
        messages.forEach(this::processMessage);
    }

    private void processMessage(BookingAvroModel bookingAvroModel) {
        VaccineCenterQueryWebClientResponse responseModel =
                queryWebClient.getVaccineCenterById(bookingAvroModel.getVaccineCenterId());
        responseModel.getVaccineStocks()
                .forEach(vaccineStock -> reduceQuantity(vaccineStock, bookingAvroModel.getVaccineType()));
        VaccineCenterIndexModel vaccineCenterIndexModel =
                responseModelToIndexModelTransformer.getVaccineCenterIndexModel(responseModel);
        String id = elasticIndexClient.save(vaccineCenterIndexModel);
        log.info("Document with id: {} has been updated in elasticsearch", id);
    }

    private void reduceQuantity(VaccineStock vaccineStock, String vaccineType) {
        if (vaccineStock.getName().equals(vaccineType)) {
            checkIfQuantityGreaterThanZero(vaccineStock);
        }
    }

    private void checkIfQuantityGreaterThanZero(VaccineStock vaccineStock) {
        if (vaccineStock.getQuantity() <= 0) {
            vaccineStock.setReserve(vaccineStock.getReserve() - 1);
        } else {
            vaccineStock.setQuantity(vaccineStock.getQuantity() - 1);
        }
    }
}
