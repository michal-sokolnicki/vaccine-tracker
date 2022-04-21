package com.vaccinetracker.elastic.index.client.service.impl;

import com.vaccinetracker.config.ElasticConfigData;
import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.index.client.util.ElasticIndexUtil;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "false")
public class BookingElasticIndexClient implements ElasticIndexClient<BookingIndexModel> {

    private final ElasticConfigData elasticConfigData;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticIndexUtil<BookingIndexModel> elasticIndexUtil;

    public BookingElasticIndexClient(ElasticConfigData elasticConfigData, ElasticsearchOperations elasticsearchOperations,
                                     ElasticIndexUtil<BookingIndexModel> elasticIndexUtil) {
        this.elasticConfigData = elasticConfigData;
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticIndexUtil = elasticIndexUtil;
    }

    @Override
    public String save(BookingIndexModel document) {
        IndexQuery indexQuery = elasticIndexUtil.getIndexQuery(document);
        String documentId = elasticsearchOperations.index(indexQuery,
                IndexCoordinates.of(elasticConfigData.getBookingIndexName()));
        log.info("Documents indexed successfully with type: {} and ids: {}",
                BookingIndexModel.class.getName(), documentId);
        return documentId;
    }

    @Override
    public List<String> saveAll(List<BookingIndexModel> documents) {
        List<IndexQuery> indexQueries = elasticIndexUtil.getIndexQueries(documents);
        List<String> documentIds = elasticsearchOperations.bulkIndex(indexQueries,
                        IndexCoordinates.of(elasticConfigData.getBookingIndexName()))
                        .stream()
                        .map(IndexedObjectInformation::getId)
                        .collect(Collectors.toList());
        log.info("Documents indexed successfully with type: {} and ids: {}",
                BookingIndexModel.class.getName(), documentIds);
        return documentIds;
    }
}
