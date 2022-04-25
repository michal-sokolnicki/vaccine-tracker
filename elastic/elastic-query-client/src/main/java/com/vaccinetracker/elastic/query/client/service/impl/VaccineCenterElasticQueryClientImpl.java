package com.vaccinetracker.elastic.query.client.service.impl;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.elastic.query.client.repository.VaccineCenterElasticsearchQueryRepository;
import com.vaccinetracker.elastic.query.client.service.VaccineCenterElasticQueryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VaccineCenterElasticQueryClientImpl extends ElasticRepositoryQueryClientBase<VaccineCenterIndexModel>
        implements VaccineCenterElasticQueryClient<VaccineCenterIndexModel> {

    private final VaccineCenterElasticsearchQueryRepository vaccineCenterElasticsearchQueryRepository;

    public VaccineCenterElasticQueryClientImpl(VaccineCenterElasticsearchQueryRepository vaccineCenterElasticsearchQueryRepository) {
        super(vaccineCenterElasticsearchQueryRepository);
        this.vaccineCenterElasticsearchQueryRepository = vaccineCenterElasticsearchQueryRepository;
    }

    @Override
    public VaccineCenterIndexModel getVaccineCenterById(String id) {
        return getById(id);
    }

    @Override
    public List<VaccineCenterIndexModel> searchVaccineCentersByText(String text) {
        List<VaccineCenterIndexModel> searchResults =
                vaccineCenterElasticsearchQueryRepository.findByTextWithPositiveQuantities(text);
        log.info("{} of document with text {} retrieved successfully", searchResults.size(), text);
        return searchResults;
    }
}
