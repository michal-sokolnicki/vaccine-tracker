package com.vaccinetracker.vaccinecenter.service.impl;

import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import com.vaccinetracker.vaccinecenter.model.UserRequest;
import com.vaccinetracker.vaccinecenter.model.VaccineCenterRequest;
import com.vaccinetracker.vaccinecenter.service.KafkaService;
import com.vaccinetracker.vaccinecenter.service.RegisterService;
import com.vaccinetracker.vaccinecenter.service.VaccineCenterService;
import com.vaccinetracker.vaccinecenter.service.transformer.VaccineCenterToIndexModelTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccineCenterServiceImpl implements VaccineCenterService {

    private final RegisterService registerService;
    private final VaccineCenterToIndexModelTransformer vaccineCenterToIndexModelTransformer;
    private final ElasticIndexClient<VaccineCenterIndexModel> elasticIndexClient;
    private final KafkaService kafkaService;

    @Override
    public void registerUser(UserRequest userRequest) {
        registerService.registerUser(userRequest);
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
        kafkaService.processMessages(messages);
    }

    @Override
    public void publishVaccinationCompleted(String id) {
        kafkaService.publishVaccinationCompleted(id);
    }
}
