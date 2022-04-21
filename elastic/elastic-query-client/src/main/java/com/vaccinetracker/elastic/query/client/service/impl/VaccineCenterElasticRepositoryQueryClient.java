package com.vaccinetracker.elastic.query.client.service.impl;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.elastic.query.client.repository.VaccineCenterElasticsearchQueryRepository;
import com.vaccinetracker.elastic.query.client.service.ElasticQueryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Primary
@Service
public class VaccineCenterElasticRepositoryQueryClient extends ElasticRepositoryQueryClientBase<VaccineCenterIndexModel>
        implements ElasticQueryClient<VaccineCenterIndexModel> {

    private final VaccineCenterElasticsearchQueryRepository vaccineCenterElasticsearchQueryRepository;

    public VaccineCenterElasticRepositoryQueryClient(VaccineCenterElasticsearchQueryRepository vaccineCenterElasticsearchQueryRepository) {
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
                vaccineCenterElasticsearchQueryRepository.findByNameOrAddress(text);
        log.info("{} of document with text {} retrieved successfully", searchResults.size(), text);
        return searchResults;
    }
}
