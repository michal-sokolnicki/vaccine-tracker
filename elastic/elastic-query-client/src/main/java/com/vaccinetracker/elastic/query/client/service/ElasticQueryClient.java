package com.vaccinetracker.elastic.query.client.service;

import com.vaccinetracker.elastic.model.IndexModel;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;

import java.util.List;
import java.util.Map;

public interface ElasticQueryClient<T extends IndexModel> {

    T getIndexModelById(String id);
    List<T> getIndexModelsByField(String value);
    List<T> getIndexModelsByParams(String... params);
    List<T> getIndexModelsByText(String text);
}
