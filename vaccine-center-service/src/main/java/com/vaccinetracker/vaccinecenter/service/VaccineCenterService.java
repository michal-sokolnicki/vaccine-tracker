package com.vaccinetracker.vaccinecenter.service;

import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import com.vaccinetracker.vaccinecenter.model.UserRequest;
import com.vaccinetracker.vaccinecenter.model.VaccineCenterRequest;

import java.util.List;

public interface VaccineCenterService {

    void registerUser(UserRequest userRequest);
    void updateStock(String id, VaccineCenterRequest vaccineCenterRequest);
    void processMessages(List<BookingAvroModel> messages);
    void publishVaccinationCompleted(String bookingId);
}
