package com.sami.hotel.controller;

import com.sami.hotel.entity.Booking;
import com.sami.hotel.entity.Room;
import com.sami.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@Log4j2
@RequiredArgsConstructor
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping(path = "/findAllRooms")
    public List<Room> getAllRooms() {
        return bookingService.getAllRooms();
    }

    @GetMapping(path = "/findBookings/{userId}")
    public List<Booking> getBookings(
            @PathVariable("userId") @NotNull Long userId) {
        return bookingService.findBookingByUserId(userId);
    }

    @GetMapping(path = "/findRoomBookedDates/{roomId}")
    public List<String> getRoomBookedDates(
            @PathVariable("roomId") @NotNull Long roomId) {
        return bookingService.getRoomBookedDates(roomId);
    }

    @PostMapping(path = "/bookRoom")
    public void bookRoom(@RequestBody @NotNull Booking booking) throws Exception {
        bookingService.bookRoom(booking);
    }

    @PostMapping(path = "/cancelBooking/{bookingId}")
    public void cancelBooking(@PathVariable("bookingId") @NotNull Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    @PostMapping(path = "updateBookingStatus/{bookingId}/{bookingStatusId}")
    public void updateBookingStatus(
            @PathVariable("bookingId") @NotNull Long bookingId,
            @PathVariable("bookingStatusId") @NotNull Long bookingStatusId) {
        bookingService.updateBookingStatus(
                bookingId,
                bookingStatusId);
    }

    @PostMapping(path = "updateBookingDate/{bookingId}/{checkInDate}/{checkoutDate}")
    public void updateBookingDate(
            @PathVariable("bookingId") @NotNull Long bookingId,
            @PathVariable("checkInDate") @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull LocalDate checkInDate,
            @PathVariable("checkOutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull LocalDate checkOutDate) {
        bookingService.updateBookingDate(
                bookingId,
                checkInDate,
                checkOutDate
        );
    }
}
