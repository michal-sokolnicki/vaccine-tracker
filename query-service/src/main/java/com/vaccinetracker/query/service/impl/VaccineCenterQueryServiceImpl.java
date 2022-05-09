package com.vaccinetracker.query.service.impl;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.elastic.query.client.service.VaccineCenterElasticQueryClient;
import com.vaccinetracker.query.model.VaccineCenterQueryResponse;
import com.vaccinetracker.query.service.VaccineCenterQueryService;
import com.vaccinetracker.query.service.transformer.IndexModelToResponseTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccineCenterQueryServiceImpl implements VaccineCenterQueryService {

    private final IndexModelToResponseTransformer indexModelToResponseTransformer;
    private final VaccineCenterElasticQueryClient<VaccineCenterIndexModel> vaccineCenterElasticQueryClient;

    @Override
    public VaccineCenterQueryResponse getVaccineCenterById(String id) {
        log.info("Querying document by id: {}", id);
        VaccineCenterIndexModel vaccineCenterIndexModel = vaccineCenterElasticQueryClient.getVaccineCenterById(id);
        return indexModelToResponseTransformer.getVaccineCenterResponseModel(vaccineCenterIndexModel);
    }

    @Override
    public List<VaccineCenterQueryResponse> searchByText(String text) {
        log.info("Searching all documents by text: {}", text);
        List<VaccineCenterIndexModel> vaccineCenterIndexModels =
                vaccineCenterElasticQueryClient.searchVaccineCentersByText(text);
        return indexModelToResponseTransformer.getVaccineCenterResponseModels(vaccineCenterIndexModels);
    }
}
