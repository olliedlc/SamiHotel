package com.sami.hotel.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class BookingStatus {

    @Id
    @SequenceGenerator(
            name = "bookingstatus_sequnce",
            sequenceName = "bookingstatus_sequnce",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bookingstatus_sequnce"
    )
    private Long id;
    private String bookingStatus;

    public BookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
