package com.vaccinetracker.booking.service;

import com.vaccinetracker.booking.model.BookingRequest;
import com.vaccinetracker.kafka.avro.model.VaccinationStatusAvroModel;

import java.util.List;

public interface KafkaService {

    void processMessages(List<VaccinationStatusAvroModel> messages);
    void publishBooking(String id, BookingRequest bookingRequest);
}
