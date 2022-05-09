package com.vaccinetracker.booking.service.transformer;

import com.vaccinetracker.elastic.model.entity.Status;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.webclient.query.model.BookingQueryWebClientResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseModelToIndexModelTransformer {

    public BookingIndexModel getBookingIndexModel(BookingQueryWebClientResponse responseModel, String status) {
        return BookingIndexModel.builder()
                .bookingId(responseModel.getId())
                .firstname(responseModel.getFirstname())
                .surname(responseModel.getSurname())
                .govId(responseModel.getGovId())
                .vaccineCenterId(responseModel.getVaccineCenterId())
                .vaccineCenterName(responseModel.getVaccineCenterName())
                .address(responseModel.getAddress())
                .vaccineType(responseModel.getVaccineType())
                .term(responseModel.getTerm())
                .status(Status.valueOf(status))
                .build();
    }
}
