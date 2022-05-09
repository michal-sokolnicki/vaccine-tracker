package com.vaccinetracker.query.service.transformer;

import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.query.model.BookingQueryResponse;
import com.vaccinetracker.query.model.VaccineCenterQueryResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IndexModelToResponseTransformer {

    public BookingQueryResponse getBookingResponseModel(BookingIndexModel bookingIndexModel) {
        return BookingQueryResponse.builder()
                .id(bookingIndexModel.getId())
                .firstname(bookingIndexModel.getFirstname())
                .surname(bookingIndexModel.getSurname())
                .govId(bookingIndexModel.getGovId())
                .vaccineCenterId(bookingIndexModel.getVaccineCenterId())
                .vaccineCenterName(bookingIndexModel.getVaccineCenterName())
                .address(bookingIndexModel.getAddress())
                .vaccineType(bookingIndexModel.getVaccineType())
                .term(bookingIndexModel.getStringTerm())
                .status(bookingIndexModel.getStatus())
                .build();
    }

    public List<BookingQueryResponse> getBookingResponseModels(List<BookingIndexModel> bookingIndexModels) {
        return bookingIndexModels.stream()
                .map(this::getBookingResponseModel)
                .collect(Collectors.toList());
    }

    public VaccineCenterQueryResponse getVaccineCenterResponseModel(
            VaccineCenterIndexModel vaccineCenterIndexModel) {
        return VaccineCenterQueryResponse.builder()
                .id(vaccineCenterIndexModel.getId())
                .name(vaccineCenterIndexModel.getName())
                .address(vaccineCenterIndexModel.getAddress())
                .vaccineStocks(vaccineCenterIndexModel.getVaccineStocks())
                .build();
    }

    public List<VaccineCenterQueryResponse> getVaccineCenterResponseModels(
            List<VaccineCenterIndexModel> vaccineCenterIndexModels) {
        return vaccineCenterIndexModels.stream()
                .map(this::getVaccineCenterResponseModel)
                .collect(Collectors.toList());
    }
}
