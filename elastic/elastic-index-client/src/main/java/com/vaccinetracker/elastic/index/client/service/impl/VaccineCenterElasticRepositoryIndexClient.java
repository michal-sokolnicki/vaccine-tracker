package com.vaccinetracker.elastic.index.client.service.impl;

import com.vaccinetracker.elastic.index.client.service.repository.VaccineCenterElasticsearchIndexRepository;
import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VaccineCenterElasticRepositoryIndexClient implements ElasticIndexClient<VaccineCenterIndexModel> {

    private final VaccineCenterElasticsearchIndexRepository vaccineCenterElasticsearchIndexRepository;

    public VaccineCenterElasticRepositoryIndexClient(VaccineCenterElasticsearchIndexRepository vaccineCenterElasticsearchIndexRepository) {
        this.vaccineCenterElasticsearchIndexRepository = vaccineCenterElasticsearchIndexRepository;
    }

    @Override
    public String save(VaccineCenterIndexModel vaccineCenterIndexModel) {
        vaccineCenterElasticsearchIndexRepository.save(vaccineCenterIndexModel);
        String id = vaccineCenterIndexModel.getId();
        log.info("Document indexed successfully with type: {} and id: {}", VaccineCenterIndexModel.class.getName(), id);
        return id;
    }

    @Override
    public List<String> saveAll(List<VaccineCenterIndexModel> vaccineCenterIndexModels) {
        List<VaccineCenterIndexModel> bookingIndexModels = (List<VaccineCenterIndexModel>)
                vaccineCenterElasticsearchIndexRepository.saveAll(vaccineCenterIndexModels);
        List<String> ids = bookingIndexModels.stream()
                .map(VaccineCenterIndexModel::getId)
                .collect(Collectors.toList());
        log.info("Documents indexed successfully with type: {} and ids: {}", VaccineCenterIndexModel.class.getName(), ids);
        return ids;
    }
}
