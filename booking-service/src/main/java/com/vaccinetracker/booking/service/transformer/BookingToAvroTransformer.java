package com.vaccinetracker.booking.service.transformer;

import com.vaccinetracker.booking.model.BookingRequest;
import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import org.springframework.stereotype.Component;

@Component
public class BookingToAvroTransformer {

    public BookingAvroModel getBookingAvroModel(String id, BookingRequest bookingRequest) {
        return BookingAvroModel.newBuilder()
                .setBookingId(id)
                .setVaccineCenterId(bookingRequest.getVaccineCenterId())
                .setVaccineType(bookingRequest.getVaccineType())
                .build();
    }
}
