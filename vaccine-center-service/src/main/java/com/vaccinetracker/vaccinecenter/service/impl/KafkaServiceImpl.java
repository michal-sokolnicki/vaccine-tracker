package com.vaccinetracker.vaccinecenter.service.impl;

import com.vaccinetracker.config.KafkaConfigData;
import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.entity.VaccineStock;
import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import com.vaccinetracker.kafka.avro.model.VaccinationStatusAvroModel;
import com.vaccinetracker.kafka.producer.service.KafkaProducer;
import com.vaccinetracker.vaccinecenter.service.KafkaService;
import com.vaccinetracker.vaccinecenter.service.QueryWebClient;
import com.vaccinetracker.vaccinecenter.service.transformer.ResponseModelToIndexModelTransformer;
import com.vaccinetracker.webclient.query.model.VaccineCenterQueryWebClientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private static final String COMPLETED = "COMPLETED";
    private static final String COMPLETED_MESSAGE = "Vaccination successfully completed";

    private final QueryWebClient queryWebClient;
    private final ResponseModelToIndexModelTransformer responseModelToIndexModelTransformer;
    private final ElasticIndexClient<VaccineCenterIndexModel> elasticIndexClient;
    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducer<String, VaccinationStatusAvroModel> kafkaProducer;

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

    @Override
    public void publishVaccinationCompleted(String bookingId) {
        VaccinationStatusAvroModel vaccinationStatusAvroModel = getVaccinationStatusAvroModel(bookingId);
        kafkaProducer.send(kafkaConfigData.getProducerTopicName(), bookingId,
                vaccinationStatusAvroModel);
    }

    private VaccinationStatusAvroModel getVaccinationStatusAvroModel(String bookingId) {
        return VaccinationStatusAvroModel.newBuilder()
                .setBookingId(bookingId)
                .setStatus(COMPLETED)
                .setMessage(COMPLETED_MESSAGE)
                .build();
    }
}
