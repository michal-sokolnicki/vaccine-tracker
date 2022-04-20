package com.vaccinetracker.elastic.index.client.util;

import com.vaccinetracker.elastic.model.IndexModel;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticIndexUtil<T extends IndexModel> {

    public IndexQuery getIndexQuery(T document) {
        return createIndexQuery(document);
    }

    private IndexQuery createIndexQuery(T document) {
        return new IndexQueryBuilder()
                .withId(document.getId())
                .withObject(document)
                .build();
    }

    public List<IndexQuery> getIndexQueries(List<T> documents){
        return documents.stream()
                .map(this::createIndexQuery)
                .collect(Collectors.toList());
    }
}
