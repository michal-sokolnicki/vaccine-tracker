package com.vaccinetracker.elastic.query.client.service.impl;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.elastic.query.client.repository.VaccineCenterElasticsearchQueryRepository;
import com.vaccinetracker.elastic.query.client.service.ElasticQueryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class VaccineCenterElasticQueryClient extends ElasticRepositoryQueryClientBase<VaccineCenterIndexModel>
        implements ElasticQueryClient<VaccineCenterIndexModel> {

    private final VaccineCenterElasticsearchQueryRepository vaccineCenterElasticsearchQueryRepository;

    public VaccineCenterElasticQueryClient(VaccineCenterElasticsearchQueryRepository vaccineCenterElasticsearchQueryRepository) {
        super(vaccineCenterElasticsearchQueryRepository);
        this.vaccineCenterElasticsearchQueryRepository = vaccineCenterElasticsearchQueryRepository;
    }

    @Override
    public VaccineCenterIndexModel getIndexModelById(String id) {
        return getById(id);
    }

    @Override
    public List<VaccineCenterIndexModel> getIndexModelsByField(String value) {
        return Collections.emptyList();
    }

    @Override
    public List<VaccineCenterIndexModel> getIndexModelsByParams(String... params) {
        return Collections.emptyList();
    }

    @Override
    public List<VaccineCenterIndexModel> getIndexModelsByText(String text) {
        List<VaccineCenterIndexModel> searchResults =
                vaccineCenterElasticsearchQueryRepository.findByTextWithPositiveQuantities(text);
        log.info("{} of document with text {} retrieved successfully", searchResults.size(), text);
        return searchResults;
    }
}
