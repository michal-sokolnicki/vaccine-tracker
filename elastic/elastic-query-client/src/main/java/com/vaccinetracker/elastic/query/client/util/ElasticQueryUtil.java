package com.vaccinetracker.elastic.query.client.util;

import com.vaccinetracker.elastic.model.IndexModel;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Component
public class ElasticQueryUtil <T extends IndexModel> {

    public Query getSearchQueryById(String id) {
        return new NativeSearchQueryBuilder()
                .withIds(id)
                .build();
    }

    public Query getSearchQueryByParams(Map<String, String> params) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        params.forEach((key, value) -> query.must(QueryBuilders.matchQuery(key, value)));
        return new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();
    }

    public Query getSearchQueryByText(List<String> fields, String text) {
        MultiMatchQueryBuilder multiMatchQuery = multiMatchQuery(text);
        fields.forEach(multiMatchQuery::field);
        return new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .build();
    }
}
