package com.vaccinetracker.elastic.query.client.service.impl;

import com.vaccinetracker.config.ElasticConfigData;
import com.vaccinetracker.config.ElasticQueryConfigData;
import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.elastic.query.client.service.ElasticQueryClient;
import com.vaccinetracker.elastic.query.client.util.ElasticQueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class VaccineCenterElasticQueryClient extends ElasticQueryClientBase<VaccineCenterIndexModel>
        implements ElasticQueryClient<VaccineCenterIndexModel> {

    private final ElasticConfigData elasticConfigData;
    private final ElasticQueryConfigData elasticQueryConfigData;

    public VaccineCenterElasticQueryClient(ElasticConfigData elasticConfigData,
                                           ElasticQueryUtil<VaccineCenterIndexModel> elasticQueryUtil,
                                           ElasticQueryConfigData elasticQueryConfigData,
                                           ElasticsearchOperations elasticsearchOperations) {
        super(elasticQueryUtil, elasticsearchOperations, VaccineCenterIndexModel.class);
        this.elasticConfigData = elasticConfigData;
        this.elasticQueryConfigData = elasticQueryConfigData;
    }

    @Override
    public VaccineCenterIndexModel getIndexModelById(String id) {
        return getById(id, elasticConfigData.getVaccineCenterIndexName());
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
        return getByText(text, elasticConfigData.getVaccineCenterIndexName(),
                elasticQueryConfigData.getVaccineCenterFields());
    }
}
