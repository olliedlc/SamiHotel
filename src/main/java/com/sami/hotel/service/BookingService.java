package com.sami.hotel.service;

import com.sami.hotel.entity.Booking;
import com.sami.hotel.entity.Room;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface BookingService {

    List<Booking> getAllActiveBooking();

    List<Room> getAllRooms();

    List<Booking> findBookingByUserId(Long userId);

    void bookRoom(Booking booking) throws Exception;

    void cancelBooking(Long bookingId);

    void updateBookingStatus(Long bookingId, Long bookingStatusId);

    void updateBookingDate(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate);

    List<String> getRoomBookedDates(Long roomId);
}
