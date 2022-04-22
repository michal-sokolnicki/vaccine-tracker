package com.vaccinetracker.booking.service.transformer;

import com.vaccinetracker.booking.entity.BookingRequest;
import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import org.springframework.stereotype.Component;

@Component
public class BookingToAvroTransformer {

    public BookingAvroModel getBookingAvroModel(BookingRequest bookingRequest) {
        return BookingAvroModel.newBuilder()
                .setVaccineCenterId(bookingRequest.getVaccineCenterId())
                .setVaccineType(bookingRequest.getVaccineType())
                .build();
    }
}
