package com.vaccinetracker.query.transformer;

import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.query.model.QueryServiceResponseModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IndexModelToResponseModelTransformer {

    public QueryServiceResponseModel getResponseModel(BookingIndexModel bookingIndexModel) {
        return QueryServiceResponseModel.builder()
                .id(bookingIndexModel.getId())
                .firstname(bookingIndexModel.getFirstname())
                .surname(bookingIndexModel.getSurname())
                .govId(bookingIndexModel.getGovId())
                .vaccineCenter(bookingIndexModel.getVaccineCenter())
                .address(bookingIndexModel.getAddress())
                .vaccineType(bookingIndexModel.getVaccineType())
                .term(bookingIndexModel.getStringTerm())
                .status(bookingIndexModel.getStatus())
                .build();
    }

    public List<QueryServiceResponseModel> getResponseModels(List<BookingIndexModel> bookingIndexModels) {
        return bookingIndexModels.stream()
                .map(this::getResponseModel)
                .collect(Collectors.toList());
    }
}
