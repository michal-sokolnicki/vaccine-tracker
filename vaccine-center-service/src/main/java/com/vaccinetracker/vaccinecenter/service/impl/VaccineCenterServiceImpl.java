package com.vaccinetracker.vaccinecenter.service.impl;

import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.entity.VaccineStock;
import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import com.vaccinetracker.vaccinecenter.model.VaccineCenterRequest;
import com.vaccinetracker.vaccinecenter.query.model.VaccineCenterQueryWebClientResponse;
import com.vaccinetracker.vaccinecenter.service.VaccineCenterQueryWebClient;
import com.vaccinetracker.vaccinecenter.service.VaccineCenterService;
import com.vaccinetracker.vaccinecenter.service.transformer.ResponseModelToIndexModelTransformer;
import com.vaccinetracker.vaccinecenter.service.transformer.VaccineCenterToIndexModelTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccineCenterServiceImpl implements VaccineCenterService {

    private final VaccineCenterToIndexModelTransformer vaccineCenterToIndexModelTransformer;
    private final VaccineCenterQueryWebClient vaccineCenterQueryWebClient;
    private final ResponseModelToIndexModelTransformer responseModelToIndexModelTransformer;
    private final ElasticIndexClient<VaccineCenterIndexModel> elasticIndexClient;

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
                vaccineCenterQueryWebClient.getVaccineCenterById(bookingAvroModel.getVaccineCenterId());
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
