package com.vaccinetracker.query.service.impl;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.elastic.query.client.service.ElasticQueryClient;
import com.vaccinetracker.query.model.VaccineCenterQueryServiceResponseModel;
import com.vaccinetracker.query.service.VaccineCenterQueryService;
import com.vaccinetracker.query.transformer.IndexModelToResponseModelTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VaccineCenterQueryServiceImpl implements VaccineCenterQueryService {

    private final IndexModelToResponseModelTransformer indexModelToResponseModelTransformer;
    private final ElasticQueryClient<VaccineCenterIndexModel> elasticQueryClient;

    public VaccineCenterQueryServiceImpl(IndexModelToResponseModelTransformer indexModelToResponseModelTransformer,
                                         ElasticQueryClient<VaccineCenterIndexModel> elasticQueryClient) {
        this.indexModelToResponseModelTransformer = indexModelToResponseModelTransformer;
        this.elasticQueryClient = elasticQueryClient;
    }

    @Override
    public VaccineCenterQueryServiceResponseModel getVaccineCenterById(String id) {
        log.info("Querying document by id: {}", id);
        VaccineCenterIndexModel vaccineCenterIndexModel = elasticQueryClient.getIndexModelById(id);
        return indexModelToResponseModelTransformer.getVaccineCenterResponseModel(vaccineCenterIndexModel);
    }

    @Override
    public List<VaccineCenterQueryServiceResponseModel> searchByText(String text) {
        log.info("Searching all documents by text: {}", text);
        List<VaccineCenterIndexModel> vaccineCenterIndexModels = elasticQueryClient.getIndexModelsByText(text);
        return indexModelToResponseModelTransformer.getVaccineCenterResponseModels(vaccineCenterIndexModels);
    }
}
