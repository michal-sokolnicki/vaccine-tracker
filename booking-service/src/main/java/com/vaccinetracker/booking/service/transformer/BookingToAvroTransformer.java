package com.vaccinetracker.booking.service.transformer;

import com.vaccinetracker.booking.entity.BookingRequest;
import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import org.springframework.stereotype.Component;

@Component
public class BookingToAvroTransformer {

    public BookingAvroModel getBookingAvroModel(BookingRequest bookingRequest) {
        return BookingAvroModel.newBuilder()
                .setUserId(1)
                .setFirstName("John")
                .setSurname("Doe")
                .setVaccineCenterId(bookingRequest.getVaccineCenterId())
                .setVaccineType(bookingRequest.getVaccineType())
                .setDate(bookingRequest.getDate())
                .setTime(bookingRequest.getTime())
                .build();
    }
}
