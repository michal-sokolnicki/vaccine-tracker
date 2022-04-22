package com.vaccinetracker.vaccinecenter.service.impl;

import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.entity.VaccineStock;
import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import com.vaccinetracker.vaccinecenter.query.model.VaccineCenterQueryWebClientResponseModel;
import com.vaccinetracker.vaccinecenter.service.VaccineCenterQueryWebClient;
import com.vaccinetracker.vaccinecenter.service.VaccineCenterService;
import com.vaccinetracker.vaccinecenter.service.transformer.ResponseModelToIndexModelTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VaccineCenterServiceImpl implements VaccineCenterService {

    private final VaccineCenterQueryWebClient vaccineCenterQueryWebClient;
    private final ResponseModelToIndexModelTransformer responseModelToIndexModelTransformer;
    private final ElasticIndexClient<VaccineCenterIndexModel> elasticIndexClient;

    public VaccineCenterServiceImpl(VaccineCenterQueryWebClient vaccineCenterQueryWebClient, ResponseModelToIndexModelTransformer responseModelToIndexModelTransformer, ElasticIndexClient<VaccineCenterIndexModel> elasticIndexClient) {
        this.vaccineCenterQueryWebClient = vaccineCenterQueryWebClient;
        this.responseModelToIndexModelTransformer = responseModelToIndexModelTransformer;
        this.elasticIndexClient = elasticIndexClient;
    }

    @Override
    public void processMessages(List<BookingAvroModel> messages) {
        messages.forEach(this::processMessage);
    }

    private void processMessage(BookingAvroModel bookingAvroModel) {
        VaccineCenterQueryWebClientResponseModel responseModel =
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
