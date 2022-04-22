package com.vaccinetracker.elastic.query.client.repository.impl;

import com.vaccinetracker.config.ElasticConfigData;
import com.vaccinetracker.config.ElasticQueryConfigData;
import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.elastic.query.client.repository.CustomizedVaccineCenterElasticsearchQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Slf4j
@Service
public class CustomizedVaccineCenterElasticsearchQueryRepositoryImpl implements
        CustomizedVaccineCenterElasticsearchQueryRepository {

    private final ElasticQueryConfigData elasticQueryConfigData;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticConfigData elasticConfigData;

    public CustomizedVaccineCenterElasticsearchQueryRepositoryImpl(ElasticQueryConfigData elasticQueryConfigData, ElasticsearchOperations elasticsearchOperations, ElasticConfigData elasticConfigData) {
        this.elasticQueryConfigData = elasticQueryConfigData;
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticConfigData = elasticConfigData;
    }

    @Override
    public List<VaccineCenterIndexModel> findByTextWithPositiveQuantities(String text) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(getMultiMatchQueryBuilder(text))
                .must(getNestedQueryBuilder());
        NativeSearchQuery searchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
        return search(searchQueryBuilder, text);
    }

    private MultiMatchQueryBuilder getMultiMatchQueryBuilder(String text) {
        MultiMatchQueryBuilder multiMatchQuery = multiMatchQuery(text);
        elasticQueryConfigData.getVaccineCenterFields().forEach(multiMatchQuery::field);
        return multiMatchQuery.type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
    }

    private NestedQueryBuilder getNestedQueryBuilder() {
        BoolQueryBuilder query = QueryBuilders.boolQuery().filter(QueryBuilders
                .rangeQuery(elasticQueryConfigData.getVaccineStockQuantityField()).gte(BigInteger.ZERO));
        return QueryBuilders.nestedQuery(elasticQueryConfigData.getVaccineStockPath(), query, ScoreMode.None)
                .innerHit(new InnerHitBuilder().setFetchSourceContext(new FetchSourceContext(true,
                        new String[] {elasticQueryConfigData.getVaccineStockNameField()}, new String[0])
        ));
    }

    private List<VaccineCenterIndexModel> search(NativeSearchQuery searchQueryBuilder, String text) {
        SearchHits<VaccineCenterIndexModel> searchResult = elasticsearchOperations.search(searchQueryBuilder,
                VaccineCenterIndexModel.class, IndexCoordinates.of(elasticConfigData.getVaccineCenterIndexName()));
        log.info("{} of documents with text {} retrieved successfully", searchResult.getTotalHits(), text);
        return searchResult.get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
