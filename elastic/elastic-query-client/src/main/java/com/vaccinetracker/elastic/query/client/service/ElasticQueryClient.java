package com.vaccinetracker.elastic.query.client.service;

import com.vaccinetracker.elastic.model.IndexModel;
import java.util.List;

public interface ElasticQueryClient<T extends IndexModel> {

    T getIndexModelById(String id);
    List<T> getIndexModelsByField(String value);
    List<T> getIndexModelsByParams(String... params);
    List<T> getIndexModelsByText(String text);
}
