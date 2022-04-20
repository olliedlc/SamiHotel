package com.sami.hotel.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sami.hotel.entity.Booking;
import com.sami.hotel.entity.Room;
import com.sami.hotel.entity.RoomType;
import com.sami.hotel.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service

public class BookingServiceImpl implements BookingService{

    @Autowired private final RoomRepository roomRepository;
    @Autowired private final BookingRepository bookingRepository;
    @Autowired private final RoomTypeRepository roomTypeRepository;
    @Autowired private final BedTypeRepository bedTypeRepository;
    @Autowired private final UserRepository userRepository;

    private final Long BOOKED_STATUS = 1L;
    private final Long CHECKED_IN_STATUS = 2L;
    private final Long CHECKED_OUT_STATUS = 3L;
    private final Long CANCELLED_STATUS = 4L;

    @Override
    public List<Booking> getAllActiveBooking() {
        return bookingRepository.getAllActiveBooking();
    }

    @HystrixCommand(fallbackMethod = "getAllRoomsFallback")
    @Override
    public List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();

        for(Room room : roomRepository.findAll()) {
            RoomType roomType = roomTypeRepository.findById(
                    room.getRoomTypeId()).get();
            roomType.setBedType(
                    bedTypeRepository.findById(
                            roomType.getBedTypeId()).get());
            room.setRoomType(roomType);
            roomList.add(room);
        }
        return roomList;
    }

    @HystrixCommand(fallbackMethod = "findBookingByUserIdFallback")
    @Override
    public List<Booking> findBookingByUserId(Long userId) {
        List<Booking> bookingList = new ArrayList<>();

        for(Booking booking : bookingRepository.findBookingByUserId(userId)) {
            Room room = roomRepository.findById(
                    booking.getRoomId()).get();
            RoomType roomType = roomTypeRepository.
                    findById(room.getRoomTypeId()).get();
            roomType.setBedType(
                    bedTypeRepository.findById(
                            roomType.getBedTypeId()).get());
            room.setRoomType(roomType);
            booking.setRoom(room);
            booking.setUser(userRepository.findById(userId).get());
            bookingList.add(booking);
        }
        return bookingList;
    }

    @HystrixCommand(fallbackMethod = "bookRoomFallback")
    @Override
    public void bookRoom(@NotNull Booking booking) {
        Room room = roomRepository.findById(booking.getRoomId()).get();
        LocalDate checkInDate = booking.getCheckInDate();
        LocalDate checkOutDate = booking.getCheckOutDate();

        if(validateRoomAvailability(room)){
            booking.setRoom(room);
        }
        validateBookingDateIsNotLaterThan30Days(
                checkInDate.atStartOfDay());
        validateBookingNotOver3Days(checkInDate,checkOutDate);

        roomRepository.save(room);
        bookingRepository.save(booking);
    }

    @HystrixCommand(fallbackMethod = "cancelBookingFallback")
    @Override
    public void cancelBooking(
            Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        if(booking.getBookingStatusId() == BOOKED_STATUS) {
            booking.setBookingStatusId(CANCELLED_STATUS);
        }
        else {
            throw new RuntimeException("Cannot cancel CHECKED_IN, CHECKED_OUT, " +
                    "and CANCELLED booking status");
        }
        Room room = roomRepository.findById(booking.getRoomId()).get();
        int roomsAvailable = room.getRoomsAvailable();
        room.setRoomsAvailable(roomsAvailable + 1);
        booking.setRoom(room);
        roomRepository.save(room);
        bookingRepository.save(booking);
    }

    @HystrixCommand(fallbackMethod = "updateBookingStatusFallback")
    @Override
    public void updateBookingStatus(Long bookingId,
            Long bookingStatusId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        if(bookingStatusId != CHECKED_IN_STATUS &&
                bookingStatusId != CHECKED_OUT_STATUS) {
            throw new RuntimeException("This is used only for check-in " +
                    "and check-out purposes.");
        }
        booking.setBookingStatusId(bookingStatusId);
        bookingRepository.save(booking);
    }

    @HystrixCommand(fallbackMethod = "updateBookingDateFallback")
    @Override
    public void updateBookingDate(
            Long bookingId,
            LocalDate checkinDate,
            LocalDate checkoutDate) {
        Booking booking = bookingRepository.findById(bookingId).get();

        validateBookingDateIsNotLaterThan30Days(
                booking.getCheckInDate().atStartOfDay());
        validateBookingNotOver3Days(checkinDate,checkoutDate);

        booking.setCheckInDate(checkinDate);
        booking.setCheckOutDate(checkoutDate);
        bookingRepository.save(booking);
    }

    @HystrixCommand(fallbackMethod = "getRoomBookedDatesFallback")
    @Override
    public List<String> getRoomBookedDates(Long roomId) {
        return bookingRepository.getRoomBookedDates(roomId);
    }

    private void validateBookingDateIsNotLaterThan30Days(LocalDateTime checkInDate) {
        LocalDateTime dateTodayPlus30 = LocalDate.now()
                .atStartOfDay().plusDays(30);
        if(checkInDate.isAfter(dateTodayPlus30)) {
            throw new RuntimeException("Cannot book dates more than 30 days in advance");
        }
    }

    private void validateBookingNotOver3Days(LocalDate checkInDate, LocalDate checkOutDate) {

        if(checkOutDate.atStartOfDay().isBefore(checkInDate.atStartOfDay())) {
            throw new RuntimeException("Selected check-out date should not be" +
                    " before the check-in date.");
        }

        if(Duration.between(checkInDate.atStartOfDay(),checkOutDate.atStartOfDay()).toDays() > 3) {
            throw new RuntimeException("Cannot stay longer than 3 days");
        }
    }

    private boolean validateRoomAvailability(Room room) {

        int roomsAvailable = room.getRoomsAvailable();
        if(roomsAvailable == 0) {
            throw new RuntimeException("Selected room in not available at the moment. " +
                    "Kindly try a different room.");
        }
        room.setRoomsAvailable(roomsAvailable - 1);
        return true;
    }

    public List<Room> getAllRoomsFallback() {
        log.info("Please try again later.");
        return List.of(new Room(
                1L,
                0,
                true,
                "NO ROOM",
                0.00));
    }

    public List<Booking> findBookingByUserIdFallback(Long userId) {
        log.info("Please try again later.");
        return List.of(new Booking(
                0L,
                0L,
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                0L
        ));
    }

    public void bookRoomFallback(@NotNull Booking booking) {
        log.info("Please try again later.");
    }

    public void cancelBookingFallback(
            Long bookingId) {
        log.info("Please try again later.");
    }

    public void updateBookingStatusFallback(
            Long bookingId,
            Long bookingStatusId) {
        log.info("Please try again later.");
    }

    public void updateBookingDateFallback(
            Long bookingId,
            LocalDate checkinDate,
            LocalDate checkoutDate) {
        log.info("Please try again later.");
    }

    public List<String> getRoomBookedDatesFallback(Long roomId) {
        log.info("Please try again later.");
        return List.of("NO ROOM AVAILABLE");
    }
}
