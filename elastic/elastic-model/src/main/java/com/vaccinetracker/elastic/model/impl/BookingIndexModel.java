package com.vaccinetracker.elastic.model.impl;

import com.vaccinetracker.elastic.model.IndexModel;
import com.vaccinetracker.elastic.model.entity.Status;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@Builder
@Document(indexName = "#{@elasticConfigData.indexName}")
public class BookingIndexModel implements IndexModel {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            .withLocale(Locale.ENGLISH);

    @Id
    @Field("booking_id")
    private String bookingId;

    @Field
    private String firstname;

    @Field
    private String surname;

    @Field("gov_id")
    private String govId;

    @Field("vaccine_center")
    private String vaccineCenter;

    @Field
    private String address;

    @Field("vaccine_type")
    private String vaccineType;

    @Field
    private String term;

    @Field
    private Status status;

    @Override
    public String getId() {
        return bookingId;
    }
    
    public String getStatus() {
        return status.name();
    }

    public LocalDateTime getTerm() {
        return LocalDateTime.parse(term, formatter);
    }

    public String getStringTerm() {
        return term;
    }

    public static BookingIndexModelBuilder builder() {
        return new BookingIndexModelBuilder();
    }

    public static BookingIndexModelBuilder builder(BookingIndexModel bookingIndexModel) {
        return new BookingIndexModelBuilder(bookingIndexModel);
    }

    public static class BookingIndexModelBuilder {

        public BookingIndexModelBuilder() {}

        public BookingIndexModelBuilder(BookingIndexModel bookingIndexModel) {
            this.bookingId = bookingIndexModel.getBookingId();
            this.firstname = bookingIndexModel.getFirstname();
            this.surname = bookingIndexModel.getSurname();
            this.govId = bookingIndexModel.getGovId();
            this.vaccineCenter = bookingIndexModel.getVaccineCenter();
            this.address = bookingIndexModel.getAddress();
            this.vaccineType = bookingIndexModel.getVaccineType();
            this.term = formatter.format(bookingIndexModel.getTerm());
            this.status = Status.valueOf(bookingIndexModel.getStatus());
        }
    }
}
