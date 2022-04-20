package com.vaccinetracker.booking.service.transformer;

import com.vaccinetracker.booking.entity.BookingRequest;
import com.vaccinetracker.booking.entity.BookingRequestUpdate;
import com.vaccinetracker.elastic.model.entity.Status;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Component
public class BookingToIndexModelTransformer {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            .withLocale(Locale.ENGLISH);

    public BookingIndexModel getBookingIndexModelToCreate(BookingRequest bookingRequest) {
        return BookingIndexModel.builder()
                .bookingId(UUID.randomUUID().toString())
                .firstname(bookingRequest.getFirstname())
                .surname(bookingRequest.getSurname())
                .govId(bookingRequest.getGovId())
                .vaccineCenter(bookingRequest.getVaccineCenter())
                .address(bookingRequest.getAddress())
                .vaccineType(bookingRequest.getVaccineType())
                .term(bookingRequest.getTerm())
                .status(Status.SCHEDULED)
                .build();
    }

    public BookingIndexModel getBookingIndexModelToUpdate(BookingIndexModel bookingIndexModel,
                                                          BookingRequestUpdate bookingRequestUpdate) {
        BookingIndexModel.BookingIndexModelBuilder builder =
                BookingIndexModel.builder(bookingIndexModel);
        Optional.ofNullable(bookingRequestUpdate.getVaccineCenter()).ifPresent(builder::vaccineCenter);
        Optional.ofNullable(bookingRequestUpdate.getAddress()).ifPresent(builder::address);
        Optional.ofNullable(bookingRequestUpdate.getVaccineType()).ifPresent(builder::vaccineType);
        Optional.ofNullable(bookingRequestUpdate.getTerm()).ifPresent(builder::term);
        Optional.ofNullable(bookingRequestUpdate.getStatus())
                .map(Status::valueOf)
                .ifPresent(builder::status);
        return builder.build();
    }
}
