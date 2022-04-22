package com.vaccinetracker.vaccinecenter.service;

import com.vaccinetracker.kafka.avro.model.BookingAvroModel;

import java.util.List;

public interface VaccineCenterService {

    void processMessages(List<BookingAvroModel> messages);
}
