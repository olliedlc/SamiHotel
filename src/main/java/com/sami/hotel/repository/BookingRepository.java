package com.sami.hotel.repository;

import com.sami.hotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query(nativeQuery = true,value = "SELECT * FROM booking s WHERE s.user_id = ?1")
    List<Booking> findBookingByUserId(Long userId);

    @Query(nativeQuery = true,value = "SELECT s.check_in_date,s.check_out_date " +
            "FROM booking s WHERE s.room_id = ?1 AND s.booking_status_id IN (1,2)")
    List<String> getRoomBookedDates(Long roomId);

    @Query(nativeQuery = true,value = "SELECT * FROM booking s WHERE " +
            "s.booking_status_id IN (1,2)")
    List<Booking> getAllActiveBooking();
}
