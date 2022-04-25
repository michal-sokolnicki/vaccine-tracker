package com.vaccinetracker.elastic.query.client.service;

import com.vaccinetracker.elastic.model.IndexModel;

import java.util.List;

public interface BookingElasticQueryClient<T extends IndexModel> {

    T getBookingById(String id);
    List<T> getBookingByGovId(String govId);
    List<T> getBookingByGovIdAndStatus(String govId, String status);
    List<T> getByGovIdWithDateRange(String govId, String from, String to);
    List<T> getByVaccineCenterIdWithDateRange(String vaccineCenterId, String from, String to);
    List<T> searchBookingByText(String text);
}
