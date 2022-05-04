package com.vaccinetracker.booking.service.transformer;

import com.vaccinetracker.booking.model.BookingRequest;
import com.vaccinetracker.elastic.model.entity.Status;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.security.common.UserContext;
import com.vaccinetracker.security.common.UserDetails;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookingToIndexModelTransformer {

    public BookingIndexModel getBookingIndexModelToCreate(BookingRequest bookingRequest) {
        BookingIndexModel.BookingIndexModelBuilder builder = BookingIndexModel.builder()
                .bookingId(UUID.randomUUID().toString())
                .status(Status.SCHEDULED);
        fillBuilderWithData(builder, bookingRequest);
        return builder.build();
    }

    private void fillBuilderWithData(
            BookingIndexModel.BookingIndexModelBuilder builder, BookingRequest bookingRequest) {
        UserDetails userDetails = UserContext.getUserDetails();
        builder.firstname(userDetails.getFirstname())
                .surname(userDetails.getLastname())
                .govId(userDetails.getGovId())
                .vaccineCenterId(bookingRequest.getVaccineCenterId())
                .vaccineCenterName(bookingRequest.getVaccineCenterName())
                .address(bookingRequest.getAddress())
                .vaccineType(bookingRequest.getVaccineType())
                .term(bookingRequest.getTerm());
    }

    public BookingIndexModel getBookingIndexModelToUpdate(String id, BookingRequest bookingRequest) {
        BookingIndexModel.BookingIndexModelBuilder builder = BookingIndexModel.builder()
                .bookingId(id)
                .status(Status.valueOf(bookingRequest.getStatus()));
        fillBuilderWithData(builder, bookingRequest);
        return builder.build();
    }
}
