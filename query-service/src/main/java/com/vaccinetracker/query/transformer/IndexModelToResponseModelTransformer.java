package com.vaccinetracker.query.transformer;

import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.query.model.BookingQueryServiceResponseModel;
import com.vaccinetracker.query.model.VaccineCenterQueryServiceResponseModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IndexModelToResponseModelTransformer {

    public BookingQueryServiceResponseModel getBookingResponseModel(BookingIndexModel bookingIndexModel) {
        return BookingQueryServiceResponseModel.builder()
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

    public List<BookingQueryServiceResponseModel> getBookingResponseModels(List<BookingIndexModel> bookingIndexModels) {
        return bookingIndexModels.stream()
                .map(this::getBookingResponseModel)
                .collect(Collectors.toList());
    }

    public VaccineCenterQueryServiceResponseModel getVaccineCenterResponseModel(
            VaccineCenterIndexModel vaccineCenterIndexModel) {
        return VaccineCenterQueryServiceResponseModel.builder()
                .id(vaccineCenterIndexModel.getId())
                .name(vaccineCenterIndexModel.getName())
                .address(vaccineCenterIndexModel.getAddress())
                .vaccineStocks(vaccineCenterIndexModel.getVaccineStocks())
                .build();
    }

    public List<VaccineCenterQueryServiceResponseModel> getVaccineCenterResponseModels(
            List<VaccineCenterIndexModel> vaccineCenterIndexModels) {
        return vaccineCenterIndexModels.stream()
                .map(this::getVaccineCenterResponseModel)
                .collect(Collectors.toList());
    }
}
