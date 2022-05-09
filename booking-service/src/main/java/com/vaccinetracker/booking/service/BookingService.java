package com.vaccinetracker.booking.service;

import com.vaccinetracker.booking.model.BookingRequest;
import com.vaccinetracker.kafka.avro.model.VaccinationStatusAvroModel;

import java.util.List;

public interface BookingService {

    void booking(BookingRequest bookingRequest);
    void updateBooking(String id, BookingRequest bookingRequest);
    void processMessages(List<VaccinationStatusAvroModel> messages);
}
