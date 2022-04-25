package com.vaccinetracker.vaccinecenter.service;

import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import com.vaccinetracker.vaccinecenter.model.VaccineCenterRequest;

import java.util.List;

public interface VaccineCenterService {

    void updateStock(String id, VaccineCenterRequest vaccineCenterRequest);
    void processMessages(List<BookingAvroModel> messages);
}
