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

import static com.vaccinetracker.elastic.model.util.IndexModelUtil.BOOKING_FORMATTER;

@Data
@Builder
@Document(indexName = "#{@elasticConfigData.bookingIndexName}")
public class BookingIndexModel implements IndexModel {

    @Id
    @Field("booking_id")
    private String bookingId;

    @Field
    private String firstname;

    @Field
    private String surname;

    @Field("gov_id")
    private String govId;

    @Field("vaccine_center_id")
    private String vaccineCenterId;

    @Field("vaccine_center_name")
    private String vaccineCenterName;

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
        return LocalDateTime.parse(term, BOOKING_FORMATTER);
    }

    public String getStringTerm() {
        return term;
    }
}
