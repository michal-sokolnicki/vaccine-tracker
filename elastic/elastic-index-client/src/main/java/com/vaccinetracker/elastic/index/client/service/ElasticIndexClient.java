package com.vaccinetracker.elastic.index.client.service;

import com.vaccinetracker.elastic.model.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {

    String save(T document);
    List<String> saveAll(List<T> documents);
}
