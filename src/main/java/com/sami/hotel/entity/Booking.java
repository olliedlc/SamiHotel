package com.sami.hotel.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Booking {

    @Id
    @SequenceGenerator(
            name = "booking_sequence",
            sequenceName = "booking_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "booking_sequence"
    )
    private Long id;

    private Long userId;
    private Long roomId;

    //Transient variables
    @Transient private Room room;
    @Transient private User user;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private LocalDate bookingDate;
    private Long bookingStatusId;

    public Booking(
            Long userId,
            Long roomId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            LocalDate bookingDate,
            Long bookingStatusId) {
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.bookingStatusId = bookingStatusId;
    }
}